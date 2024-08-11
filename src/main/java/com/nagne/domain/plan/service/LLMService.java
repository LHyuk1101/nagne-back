package com.nagne.domain.plan.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.nagne.domain.place.entity.Place;
import com.nagne.domain.place.repository.PlaceRepository;
import com.nagne.domain.plan.dto.PlanRequestDto;
import com.nagne.domain.plan.dto.PlanResponseDto;
import com.nagne.domain.plan.entity.Plan;
import com.nagne.domain.plan.exception.LLMParsingException;
import com.nagne.domain.plan.repository.PlanRepository;
import com.nagne.domain.template.entity.Template;
import com.nagne.domain.template.repository.TemplateRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class LLMService {
  
  private final RestTemplate llmRestTemplate;
  private final PlanRepository planRepository;
  private final TemplateRepository templateRepository;
  private final PlaceRepository placeRepository;
  private final DistanceCalculationService distanceCalculationService;
  
  @Value("${llm.api.url}")
  private String apiUrl;
  
  public LLMService(@Qualifier("llmRestTemplate") RestTemplate llmRestTemplate,
    PlanRepository planRepository,
    TemplateRepository templateRepository,
    PlaceRepository placeRepository,
    DistanceCalculationService distanceCalculationService) {
    this.llmRestTemplate = llmRestTemplate;
    this.planRepository = planRepository;
    this.templateRepository = templateRepository;
    this.placeRepository = placeRepository;
    this.distanceCalculationService = distanceCalculationService;
  }
  
  public CompletableFuture<List<Plan>> generateAndSavePlans(PlanRequestDto request) {
    return CompletableFuture.supplyAsync(() -> {
      List<PlanRequestDto.PlaceDistance> distances = distanceCalculationService.calculateDistances(
        request.getPlaces());
      String llmInput = createLLMInput(request, distances);
      String llmResponse = callLLMApi(llmInput);
      PlanResponseDto planResponse = parseLLMResponse(llmResponse);
      Plan savedPlan = savePlanAndTemplates(planResponse, request);
      return List.of(savedPlan);
    }).exceptionally(ex -> {
      log.error("Error generating and saving plans", ex);
      return List.of(); // 빈 리스트를 반환하여 예외 상황 처리
    });
  }
  
  private String createLLMInput(PlanRequestDto request,
    List<PlanRequestDto.PlaceDistance> distances) {
    StringBuilder input = new StringBuilder();
    input.append("Duration: ").append(request.getDuration()).append("\n");
    input.append("Places:\n");
    
    Map<Long, PlanRequestDto.PlaceInfo> placeMap = request.getPlaces().stream()
      .collect(Collectors.toMap(PlanRequestDto.PlaceInfo::getId, p -> p));
    
    PlanRequestDto.PlaceInfo basePlace = placeMap.get(distances.get(0).getFromPlaceId());
    input.append("Base Location: ")
      .append(basePlace.getName())
      .append(" (")
      .append(basePlace.getContentType())
      .append(")\n");
    input.append("ID: ").append(basePlace.getId()).append("\n");
    input.append("Overview: ").append(basePlace.getOverview()).append("\n\n");
    
    for (PlanRequestDto.PlaceDistance distance : distances) {
      PlanRequestDto.PlaceInfo place = placeMap.get(distance.getToPlaceId());
      input.append("- ")
        .append(place.getName())
        .append(" (")
        .append(place.getContentType())
        .append(")\n");
      input.append("  ID: ").append(place.getId()).append("\n");
      input.append("  Distance from base: ").append(distance.getDistance()).append(" km\n");
      input.append("  Overview: ").append(place.getOverview()).append("\n\n");
    }
    
    return input.toString();
  }
  
  private String callLLMApi(String input) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity<Map<String, String>> request = new HttpEntity<>(Map.of("question", input), headers);
    
    try {
      ResponseEntity<String> response = llmRestTemplate.postForEntity(apiUrl, request,
        String.class);
      return response.getBody();
    } catch (Exception e) {
      log.error("Failed to call LLM API: ", e);
      throw new RuntimeException("Error occurred while calling LLM API", e);
    }
  }
  
  
  private PlanResponseDto parseLLMResponse(String llmResponse) {
    try {
      log.debug("LLM response: {}", llmResponse);
      
      ObjectMapper jsonMapper = new ObjectMapper();
      JsonNode jsonNode = jsonMapper.readTree(llmResponse);
      String yamlString = jsonNode.get("text").asText();
      
      yamlString = yamlString.replaceAll("```yaml\\n", "").replaceAll("```", "");
      
      ObjectMapper yamlMapper = new ObjectMapper(new YAMLFactory());
      yamlMapper.findAndRegisterModules();
      yamlMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
      yamlMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
      
      PlanResponseDto planResponseDto = yamlMapper.readValue(yamlString, PlanResponseDto.class);
      
      // Debugging 추가
      if (planResponseDto.getDayPlans() == null || planResponseDto.getDayPlans().isEmpty()) {
        log.error("Day plans are missing or empty in the LLM response: {}", yamlString);
        throw new LLMParsingException("Day plans are missing in the LLM response.");
      }
      
      log.debug("Parsed plan: {}", planResponseDto);
      return planResponseDto;
    } catch (Exception e) {
      log.error("Failed to parse response: {}. Error: {}", llmResponse, e.getMessage(), e);
      throw new LLMParsingException("Error occurred while parsing LLM response", e);
    }
  }
  
  
  @Transactional
  public Plan savePlanAndTemplates(PlanResponseDto dto, PlanRequestDto request) {
    String thumbnailUrl = "default_thumbnail_url";  // 기본값 설정
    
    if (!dto.getDayPlans().isEmpty() && dto.getDayPlans().get(0).getPlaces().size() > 1) {
      PlanResponseDto.PlaceDetail secondPlace = dto.getDayPlans().get(0).getPlaces().get(1);
      Long placeId = secondPlace.getPlaceId();
      
      try {
        Place place = placeRepository.findById(placeId)
          .orElseThrow(() -> new IllegalArgumentException("ID가: " + placeId + "인게 없습니다."));
        
        if (place.getThumbnailUrl() != null && !place.getThumbnailUrl().isEmpty()) {
          thumbnailUrl = place.getThumbnailUrl();
          log.info("두번째 장소 썸네일 url: {}", thumbnailUrl);
        } else {
          log.warn("두번째 장소(ID: {} 에 썸네일 url 이 없음", placeId);
        }
      } catch (IllegalArgumentException e) {
        log.error("두 번째 장소 찾는 중 문제가 생김: {}", e.getMessage());
      }
    } else {
      log.info("첫 날 계획안에 장소가 없어 기본 이미지 사용");
    }
    
    Plan plan = Plan.builder()
      .subject(dto.getSubject())
      .startDay(request.getStartDay())
      .endDay(request.getEndDay())
      .status(Plan.Status.BEGIN)
      .thumbnailUrl(thumbnailUrl)
      .type(Plan.PlanType.LLM)
      .build();
    
    Plan savedPlan = planRepository.save(plan);
    log.info("Saved plan with id: {} and thumbnail URL: {}", savedPlan.getId(),
      savedPlan.getThumbnailUrl());
    
    List<Template> templates = createTemplates(dto, savedPlan);
    templateRepository.saveAll(templates);
    log.info("Saved {} templates for plan id: {}", templates.size(), savedPlan.getId());
    
    return savedPlan;
  }
  
  private List<Template> createTemplates(PlanResponseDto dto, Plan savedPlan) {
    List<Template> templates = new ArrayList<>();
    for (PlanResponseDto.DayPlan dayPlan : dto.getDayPlans()) {
      for (PlanResponseDto.PlaceDetail placeDetail : dayPlan.getPlaces()) {
        Place place = placeRepository.findById(placeDetail.getPlaceId())
          .orElseThrow(() -> new IllegalArgumentException(
            "Place not found with title: " + placeDetail.getPlaceId()));
        
        Template template = Template.builder()
          .plan(savedPlan)
          .place(place)
          .day(dayPlan.getDay())
          .order(placeDetail.getOrder())
          .moveTime(placeDetail.getMoveTime())
          .placeSummary(placeDetail.getPlaceSummary())
          .reasoning(placeDetail.getReasoning())
          .build();
        templates.add(template);
      }
    }
    return templates;
  }
  
  private PlanResponseDto createFinalResponse(Plan savedPlan, PlanResponseDto planResponse) {
    return PlanResponseDto.builder()
      .id(savedPlan.getId())
      .userId(savedPlan.getUser().getId())
      .status(savedPlan.getStatus().name())
      .startDay(savedPlan.getStartDay())
      .endDay(savedPlan.getEndDay())
      .subject(planResponse.getSubject())
      .type(planResponse.getType())
      .dayPlans(planResponse.getDayPlans())
      .build();
  }
}
