package com.nagne.domain.plan.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.nagne.domain.place.entity.Area;
import com.nagne.domain.place.entity.Place;
import com.nagne.domain.place.repository.PlaceRepository;
import com.nagne.domain.plan.dto.PlanRequestDto;
import com.nagne.domain.plan.dto.PlanResponseDto;
import com.nagne.domain.plan.entity.Plan;
import com.nagne.domain.plan.exception.LLMParsingException;
import com.nagne.domain.plan.repository.PlanRepository;
import com.nagne.domain.template.entity.Template;
import com.nagne.domain.template.repository.TemplateRepository;
import com.nagne.domain.user.entity.User;
import com.nagne.domain.user.repository.UserRepository;
import com.nagne.global.error.exception.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
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
  private final UserRepository userRepository;
  
  @Value("${llm.api.url}")
  private String apiUrl;
  
  public LLMService(@Qualifier("llmRestTemplate") RestTemplate llmRestTemplate,
    PlanRepository planRepository,
    TemplateRepository templateRepository,
    PlaceRepository placeRepository,
    DistanceCalculationService distanceCalculationService,
    UserRepository userRepository) {
    this.llmRestTemplate = llmRestTemplate;
    this.planRepository = planRepository;
    this.templateRepository = templateRepository;
    this.placeRepository = placeRepository;
    this.distanceCalculationService = distanceCalculationService;
    this.userRepository = userRepository;
  }
  
  @Transactional
  public CompletableFuture<PlanResponseDto> generateAndSavePlan(PlanRequestDto request,
    Long userId) {
    log.info("Starting plan generation for userId: {}", userId);
    validateInput(request, userId);
    
    return CompletableFuture.supplyAsync(() -> generatePlanInternal(request, userId))
      .exceptionally(ex -> {
        log.error("Failed to generate plan for userId: {}", userId, ex);
        throw new RuntimeException("Failed to generate plan", ex);
      });
  }
  
  private void validateInput(PlanRequestDto request, Long userId) {
    log.info("Validating input for userId: {}", userId);
    if (request == null) {
      log.error("Validation failed: Request is null");
      throw new IllegalArgumentException("Request cannot be null");
    }
    if (userId == null) {
      log.error("Validation failed: UserId is null");
      throw new IllegalArgumentException("UserId cannot be null");
    }
  }
  
  private PlanResponseDto generatePlanInternal(PlanRequestDto request, Long userId) {
    try {
      log.info("Calculating distances for userId: {}", userId);
      List<PlanRequestDto.PlaceDistance> distances = distanceCalculationService.calculateDistances(
        request.getPlaces());
      
      log.info("Creating LLM input for userId: {}", userId);
      String llmInput = createLLMInput(request, distances);
      
      log.info("Calling LLM API for userId: {}", userId);
      String llmResponse = callLLMApi(llmInput);
      
      log.info("Parsing LLM response for userId: {}", userId);
      PlanResponseDto planResponse = parseLLMResponse(llmResponse);
      
      log.info("Saving plan and templates for userId: {}", userId);
      Plan savedPlan = savePlanAndTemplates(planResponse, request, userId);
      
      log.info("Plan generation completed successfully for userId: {}", userId);
      return createPlanResponseDto(savedPlan);
    } catch (Exception e) {
      log.error("Internal error while generating plan for userId: {}", userId, e);
      throw new RuntimeException("Internal error while generating plan", e);
    }
  }
  
  @Transactional(readOnly = true)
  public PlanResponseDto createPlanResponseDto(Plan plan) {
    log.info("Creating PlanResponseDto for planId: {}", plan.getId());
    List<Template> templates = templateRepository.findAllByPlanIdWithPlace(plan.getId());
    
    Map<Integer, List<PlanResponseDto.PlaceDetail>> placeDetailsByDay = templates.stream()
      .collect(Collectors.groupingBy(Template::getDay,
        Collectors.mapping(this::convertTemplateToPlaceDetail, Collectors.toList())));
    
    List<PlanResponseDto.DayPlan> dayPlans = placeDetailsByDay.entrySet().stream()
      .map(entry -> PlanResponseDto.DayPlan.builder()
        .day(entry.getKey())
        .places(entry.getValue())
        .build())
      .collect(Collectors.toList());
    
    log.info("PlanResponseDto creation completed for planId: {}", plan.getId());
    return PlanResponseDto.builder()
      .id(plan.getId())
      .userId(plan.getUser() != null ? plan.getUser().getId() : null)
      .status(plan.getStatus().name())
      .startDay(plan.getStartDay())
      .endDay(plan.getEndDay())
      .areaCode(plan.getArea() != null ? plan.getArea().getAreaCode() : null)
      .subject(plan.getSubject())
      .type(plan.getType())
      .thumbnailUrl(plan.getThumbnail())
      .dayPlans(dayPlans)
      .build();
  }
  
  private PlanResponseDto.PlaceDetail convertTemplateToPlaceDetail(Template template) {
    log.debug("Converting template to PlaceDetail for placeId: {}", template.getPlace().getId());
    return PlanResponseDto.PlaceDetail.builder()
      .placeId(template.getPlace().getId())
      .title(template.getPlace().getTitle())
      .contentType(template.getPlace().getContentTypeId().toString())
      .order(template.getOrder())
      .moveTime(template.getMoveTime())
      .placeSummary(template.getPlaceSummary())
      .reasoning(template.getReasoning())
      .placeImgUrls(template.getPlace().getThumbnailUrl())
      .build();
  }
  
  private String createLLMInput(PlanRequestDto request,
    List<PlanRequestDto.PlaceDistance> distances) {
    log.debug("Creating LLM input");
    StringBuilder input = new StringBuilder();
    input.append("Duration: ").append(request.getDuration()).append("\n");
    input.append("Place_info:\n");
    
    Map<Long, PlanRequestDto.PlaceInfo> placeMap = request.getPlaces().stream()
      .collect(Collectors.toMap(PlanRequestDto.PlaceInfo::getId, p -> p));
    PlanRequestDto.PlaceInfo basePlace = placeMap.get(distances.get(0).getFromPlaceId());
    input.append("Base Location: ")
      .append(basePlace.getName())
      .append(" (")
      .append(" contentType: ").append(basePlace.getContentType())
      .append(")\n");
    input.append("place_id: ").append(basePlace.getId()).append("\n");
    input.append("overview: ").append(basePlace.getOverview()).append("\n\n");
    
    for (PlanRequestDto.PlaceDistance distance : distances) {
      PlanRequestDto.PlaceInfo place = placeMap.get(distance.getToPlaceId());
      input.append("- ")
        .append(place.getName())
        .append(" (")
        .append(" contentType: ").append(place.getContentType())
        .append(")\n");
      input.append("  place_id: ").append(place.getId()).append("\n");
      input.append("  Distance from base: ").append(distance.getDistance()).append(" km\n");
      input.append("  overview: ").append(place.getOverview()).append("\n\n");
    }
    
    log.debug("LLM input created: {}", input.toString());
    return input.toString();
  }
  
  private String callLLMApi(String input) {
    log.debug("Calling LLM API with input: {}", input);
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity<Map<String, String>> request = new HttpEntity<>(Map.of("question", input), headers);
    
    try {
      ResponseEntity<String> response = llmRestTemplate.postForEntity(apiUrl, request,
        String.class);
      log.debug("LLM API response received");
      return response.getBody();
    } catch (Exception e) {
      log.error("Error occurred while calling LLM API", e);
      throw new RuntimeException("Error occurred while calling LLM API", e);
    }
  }
  
  private PlanResponseDto parseLLMResponse(String llmResponse) {
    log.debug("Parsing LLM response");
    try {
      ObjectMapper jsonMapper = new ObjectMapper();
      JsonNode jsonNode = jsonMapper.readTree(llmResponse);
      String yamlString = jsonNode.get("text").asText();
      
      yamlString = yamlString.replaceAll("```yaml\\n", "").replaceAll("```", "");
      
      ObjectMapper yamlMapper = new ObjectMapper(new YAMLFactory());
      yamlMapper.findAndRegisterModules();
      yamlMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
      yamlMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
      
      PlanResponseDto planResponseDto = yamlMapper.readValue(yamlString, PlanResponseDto.class);
      
      if (planResponseDto.getDayPlans() == null || planResponseDto.getDayPlans().isEmpty()) {
        log.error("Day plans are missing in the LLM response.");
        throw new LLMParsingException("Day plans are missing in the LLM response.");
      }
      log.debug("LLM response parsed successfully");
      return planResponseDto;
    } catch (Exception e) {
      log.error("Error occurred while parsing LLM response", e);
      throw new LLMParsingException("Error occurred while parsing LLM response", e);
    }
  }
  
  private void validatePlaceIds(PlanResponseDto planResponse,
    List<PlanRequestDto.PlaceInfo> originalPlaces) {
    log.debug("Validating place IDs in the response");
    Set<Long> validPlaceIds = originalPlaces.stream()
      .map(PlanRequestDto.PlaceInfo::getId)
      .collect(Collectors.toSet());
    
    for (PlanResponseDto.DayPlan dayPlan : planResponse.getDayPlans()) {
      for (PlanResponseDto.PlaceDetail placeDetail : dayPlan.getPlaces()) {
        if (!validPlaceIds.contains(placeDetail.getPlaceId())) {
          log.error("Invalid placeId found: {}", placeDetail.getPlaceId());
          throw new IllegalArgumentException("Invalid placeId: " + placeDetail.getPlaceId());
        }
      }
    }
  }
  
  @Transactional
  public Plan savePlanAndTemplates(PlanResponseDto dto, PlanRequestDto request, Long userId) {
    validatePlaceIds(dto, request.getPlaces());
    String thumbnail = "";  // 기본값 설정
    
    List<Long> placeIds = dto.getDayPlans().stream()
      .flatMap(dayPlan -> dayPlan.getPlaces().stream())
      .map(PlanResponseDto.PlaceDetail::getPlaceId)
      .filter(Objects::nonNull)
      .collect(Collectors.toList());
    List<Place> places = placeRepository.findPlaceByPlaceId(placeIds);
    Map<Long, Place> placeMap = places.stream()
      .collect(Collectors.toMap(Place::getId, Function.identity()));
    Area area = places.isEmpty() ? null : places.get(0).getArea();
    if (!dto.getDayPlans().isEmpty() && dto.getDayPlans().get(0).getPlaces().size() > 1) {
      PlanResponseDto.PlaceDetail secondPlace = dto.getDayPlans().get(0).getPlaces().get(1);
      Long placeId = secondPlace.getPlaceId();
      Place place = placeMap.get(placeId);
      if (place != null) {
        if (place.getThumbnailUrl() != null && !place.getThumbnailUrl().isEmpty()) {
          thumbnail = place.getThumbnailUrl();
        }
      }
    }
    
    User user = userRepository.findById(userId)
      .orElseThrow(() -> new EntityNotFoundException("User not find" + userId));
    
    Plan plan = Plan.builder()
      .subject(dto.getSubject())
      .startDay(request.getStartDay())
      .endDay(request.getEndDay())
      .area(area)
      .status(Plan.Status.BEGIN)
      .thumbnail(thumbnail)
      .type(Plan.PlanType.LLM)
      .user(user)
      .build();
    
    Plan savedPlan = planRepository.save(plan);
    
    List<Template> templates = createTemplates(dto, savedPlan, placeMap);
    templateRepository.saveAll(templates);
    return savedPlan;
  }
  
  private List<Template> createTemplates(PlanResponseDto dto, Plan savedPlan,
    Map<Long, Place> placeMap) {
    log.debug("Creating templates for planId: {}", savedPlan.getId());
    List<Template> templates = new ArrayList<>();
    for (PlanResponseDto.DayPlan dayPlan : dto.getDayPlans()) {
      for (PlanResponseDto.PlaceDetail placeDetail : dayPlan.getPlaces()) {
        if (placeDetail.getPlaceId() == null) {
          continue;
        }
        Place place = placeMap.get(placeDetail.getPlaceId());
        if (place == null) {
          log.error("Place not found for placeId: {}", placeDetail.getPlaceId());
          continue;
        }
        
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
    log.debug("Templates creation completed for planId: {}", savedPlan.getId());
    return templates;
  }
  
  private PlanResponseDto createFinalResponse(Plan savedPlan, PlanResponseDto planResponse) {
    log.debug("Creating final response for planId: {}", savedPlan.getId());
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
