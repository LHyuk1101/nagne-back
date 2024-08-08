package com.nagne.domain.plan.service;

import com.nagne.domain.plan.dto.PlanRequestDto;
import com.nagne.domain.plan.dto.PlanResponseDto;
import com.nagne.domain.plan.entity.Plan;
import com.nagne.domain.plan.entity.Template;
import com.nagne.domain.plan.repository.PlanRepository;
import com.nagne.domain.plan.repository.TemplateRepository;
import java.util.concurrent.CompletableFuture;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.yaml.snakeyaml.Yaml;
import java.util.Map;
import java.time.LocalDate;
import java.sql.Time;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class LLMService {
    private final RestTemplate restTemplate;
    private final PlanRepository planRepository;
    private final TemplateRepository templateRepository;
    private final String apiUrl;

    public LLMService(@Value("${llm.api.url}") String apiUrl,
        PlanRepository planRepository,
        TemplateRepository templateRepository,
        RestTemplate restTemplate) {
        this.apiUrl = apiUrl;
        this.planRepository = planRepository;
        this.templateRepository = templateRepository;
        this.restTemplate = restTemplate;
    }

    @Async
    public CompletableFuture<List<Plan>> generateAndSavePlans(PlanRequestDto request) {
        List<CompletableFuture<Plan>> futures = request.getPrompts().stream()
            .map(this::processPrompt)
            .collect(Collectors.toList());

        return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
            .thenApply(v -> futures.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList()));
    }

    private CompletableFuture<Plan> processPrompt(String prompt) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                String llmResponse = callLLMApi(prompt);
                PlanResponseDto dto = parseLLMResponse(llmResponse);
                return savePlanAndTemplates(dto);
            } catch (Exception e) {
                log.error("Error processing prompt: ", e);
                throw new RuntimeException("Failed to process prompt", e);
            }
        });
    }

    private String callLLMApi(String prompt) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, String>> request = new HttpEntity<>(Map.of("question", prompt), headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(apiUrl, request, String.class);
            return response.getBody();
        } catch (RestClientException e) {
            log.error("호출 안됨: ", e);
            throw new RuntimeException("Failed to call LLM API", e);
        }
    }

    private PlanResponseDto parseLLMResponse(String llmResponse) {
        try {
            Yaml yaml = new Yaml();
            Map<String, Object> responseMap = yaml.load(llmResponse);

            String subject = (String) responseMap.get("subject");
            List<Map<String, Object>> planDaysRaw = (List<Map<String, Object>>) responseMap.get("plan_days");

            List<PlanResponseDto.PlanDay> planDays = parsePlanDays(planDaysRaw);

            return new PlanResponseDto(subject, planDays);
        } catch (Exception e) {
            log.error("파싱이 안됐습니다ㅜㅜ: ", e);
            throw new RuntimeException("Failed to parse LLM response", e);
        }
    }

    private List<PlanResponseDto.PlanDay> parsePlanDays(List<Map<String, Object>> planDays) {
        return planDays.stream().map(dayMap -> {
            Integer day = (Integer) dayMap.get("day");
            List<Map<String, Object>> placesRaw = (List<Map<String, Object>>) dayMap.get("places");

            List<PlanResponseDto.PlanPlace> places = placesRaw.stream()
                .map(this::parsePlanPlace)
                .collect(Collectors.toList());

            return new PlanResponseDto.PlanDay(day, places);
        }).collect(Collectors.toList());
    }

    private PlanResponseDto.PlanPlace parsePlanPlace(Map<String, Object> placeMap) {
        return PlanResponseDto.PlanPlace.builder()
            .order((Integer) placeMap.get("order"))
            .title((String) placeMap.get("title"))
            .moveTime((Integer) placeMap.get("move_time"))
            .placeSummary((String) placeMap.get("place_summary"))
            .reasoning((String) placeMap.get("reasoning"))
            .build();
    }

    @Transactional
    public Plan savePlanAndTemplates(PlanResponseDto dto) {
        String placeSummary = dto.getPlanDayList().stream()
            .flatMap(day -> day.getPlanPlaceList().stream())
            .map(PlanResponseDto.PlanPlace::getPlaceSummary)
            .collect(Collectors.joining(", "));

        String reasoning = dto.getPlanDayList().stream()
            .flatMap(day -> day.getPlanPlaceList().stream())
            .map(PlanResponseDto.PlanPlace::getReasoning)
            .collect(Collectors.joining(", "));

        Plan plan = Plan.builder()
            .subject(dto.getSubject())
            .placeSummary(placeSummary)
            .reasoning(reasoning)
            .startDay(LocalDate.now())
            .endDay(LocalDate.now().plusDays(dto.getPlanDayList().size() - 1))
            .status(Plan.Status.BEGIN)
            .totalMoveTime(calculateTotalMoveTime(dto.getPlanDayList()))
            .build();

        Plan savedPlan = planRepository.save(plan);

        List<Template> templates = dto.getPlanDayList().stream()
            .flatMap(day -> day.getPlanPlaceList().stream()
                .map(place -> Template.builder()
                    .plan(savedPlan)
                    .day(day.getDay())
                    .order(place.getOrder())
                    .moveTime(Time.valueOf(String.format("%02d:00:00", place.getMoveTime())))
                    .build()))
            .collect(Collectors.toList());

        templateRepository.saveAll(templates);

        return savedPlan;
    }

    private Time calculateTotalMoveTime(List<PlanResponseDto.PlanDay> planDays) {
        int totalMinutes = planDays.stream()
            .flatMap(day -> day.getPlanPlaceList().stream())
            .mapToInt(PlanResponseDto.PlanPlace::getMoveTime)
            .sum();

        int hours = totalMinutes / 60;
        int minutes = totalMinutes % 60;
        return Time.valueOf(String.format("%02d:%02d:00", hours, minutes));
    }
}