package com.nagne.domain.plan.service;

import com.nagne.domain.place.entity.ContentType;
import com.nagne.domain.plan.dto.PlanDto;
import com.nagne.domain.plan.entity.Plan;
import com.nagne.domain.plan.repository.PlanRepository;
import com.nagne.domain.template.repository.TemplateRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlanService {

  private final PlanRepository planRepository;
  private final TemplateRepository templateRepository;

  public List<PlanDto> getAllPlans() {
    return planRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
  }

  public PlanDto getPlanById(Long id) {
    return planRepository.findById(id).map(this::convertToDTO).orElse(null);
  }

  private PlanDto convertToDTO(Plan plan) {
    List<PlanDto.PlaceDetail> placeDetails = templateRepository.findAllByPlanId(plan.getId())
      .stream()
      .map(place -> new PlanDto.PlaceDetail(place.getTitle(),
        getContentTypeName(place.getContentTypeId())))
      .collect(Collectors.toList());

    return new PlanDto(plan.getId(),
      plan.getUser().getId(),
      plan.getStatus().name(),
      plan.getStartDay(),
      plan.getEndDay(),
      plan.getThumbnailUrl(),
      placeDetails);
  }

  private String getContentTypeName(Long contentTypeId) {
    return ContentType.getNameById(contentTypeId);
  }
}