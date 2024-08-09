package com.nagne.domain.plan.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.nagne.domain.place.dto.PlaceDTO;
import com.nagne.domain.plan.dto.PlanRequestDto;
import com.nagne.domain.plan.dto.PlanResponseDto;
import com.nagne.domain.plan.entity.Plan;
import com.nagne.domain.plan.entity.Template;
import com.nagne.domain.place.entity.Place;
import com.nagne.domain.plan.repository.PlanRepository;
import com.nagne.domain.plan.repository.TemplateRepository;
import com.nagne.domain.place.repository.PlaceRepository;
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

import java.time.LocalDate;
import java.sql.Time;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class LLMService {

    private final RestTemplate llmRestTemplate;
    private final PlanRepository planRepository;
    private final TemplateRepository templateRepository;
    private final PlaceRepository placeRepository;

    @Value("${llm.api.url}")
    private String apiUrl;

    public LLMService(@Qualifier("llmRestTemplate") RestTemplate llmRestTemplate,
        PlanRepository planRepository,
        TemplateRepository templateRepository,
        PlaceRepository placeRepository) {
        this.llmRestTemplate = llmRestTemplate;
        this.planRepository = planRepository;
        this.templateRepository = templateRepository;
        this.placeRepository = placeRepository;
    }

    public CompletableFuture<List<Plan>> generateAndSavePlans(PlanRequestDto request) {
        return CompletableFuture.supplyAsync(() -> {
            List<Plan> plans = new ArrayList<>();
            for (String prompt : request.getPrompts()) {
                String llmResponse = callLLMApi(prompt);
                PlanResponseDto dto = parseLLMResponse(llmResponse);
                Plan plan = savePlanAndTemplates(dto);
                plans.add(plan);
            }
            return plans;
        });
    }

    private String callLLMApi(String prompt) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, String>> request = new HttpEntity<>(Map.of("question", prompt), headers);

        try {
            ResponseEntity<String> response = llmRestTemplate.postForEntity(apiUrl, request, String.class);
            return response.getBody();
        } catch (Exception e) {
            log.error("LLM API 호출 실패: ", e);
            throw new RuntimeException("LLM API 호출 중 오류 발생", e);
        }
    }

    private PlanResponseDto parseLLMResponse(String llmResponse) {
        try {
            log.debug("LLM 응답: {}", llmResponse);

            // JSON 파싱
            ObjectMapper jsonMapper = new ObjectMapper();
            JsonNode jsonNode = jsonMapper.readTree(llmResponse);
            String yamlString = jsonNode.get("text").asText();

            // 마크다운 코드 제거
            yamlString = yamlString.replaceAll("```yaml\\n", "").replaceAll("```", "");

            // YAML 파싱
            ObjectMapper yamlMapper = new ObjectMapper(new YAMLFactory());
            yamlMapper.findAndRegisterModules();
            yamlMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            yamlMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);

            PlanResponseDto planResponseDto = yamlMapper.readValue(yamlString, PlanResponseDto.class);

            log.debug("파싱된 계획: {}", planResponseDto);
            return planResponseDto;
        } catch (Exception e) {
            log.error("파싱 망함: {}. 오류: {}", llmResponse, e.getMessage(), e);
            throw new RuntimeException("LLM 응답 파싱 에러 ㅜㅜ", e);
        }
    }

    @Transactional
    public Plan savePlanAndTemplates(PlanResponseDto dto) {
        Plan plan = Plan.builder()
            .subject(dto.getSubject())
            .startDay(LocalDate.now())
            .endDay(LocalDate.now().plusDays(dto.getPlanDays().size() - 1))
            .status(Plan.Status.BEGIN)
            .build();

        Plan savedPlan = planRepository.save(plan);

        List<Template> templates = createTemplates(dto, savedPlan);
        templateRepository.saveAll(templates);

        return savedPlan;
    }

    private List<Template> createTemplates(PlanResponseDto dto, Plan savedPlan) {
        List<Template> templates = new ArrayList<>();
        for (PlanResponseDto.PlanDay planDay : dto.getPlanDays()) {
            for (PlanResponseDto.Place placeDto : planDay.getPlaces()) {
                Place savedPlace = createAndSavePlace(placeDto);
                Template template = Template.builder()
                    .plan(savedPlan)
                    .day(planDay.getDay())
                    .order(placeDto.getOrder())
                    .moveTime(placeDto.getMoveTime()) // 분 단위로 저장
                    .place(savedPlace)
                    .placeSummary(placeDto.getPlaceSummary())
                    .reasoning(placeDto.getReasoning())
                    .build();
                templates.add(template);
            }
        }
        return templates;
    }

    private Place createAndSavePlace(PlanResponseDto.Place placeDto) {
        Place newPlace = Place.builder()
            .title(placeDto.getTitle())
            .overview(placeDto.getPlaceSummary())
            // 필요한 다른 필드들 설정...
            .build();
        return placeRepository.save(newPlace);
    }
}