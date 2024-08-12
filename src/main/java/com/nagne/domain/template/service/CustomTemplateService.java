package com.nagne.domain.template.service;

import com.nagne.domain.plan.entity.Plan;
import com.nagne.domain.plan.repository.PlanRepository;
import com.nagne.domain.template.dto.TemplateDto;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomTemplateService {

  private final PlanRepository planRepository;

  public List<TemplateDto> getTemplatesByArea(Integer areaCode) {
    List<Plan> plans = planRepository.findByArea_AreaCode(areaCode);
    return plans.stream()
      .map(plan -> TemplateDto.builder()
        .title(plan.getSubject())
        .description(plan.getOverview())
        .thumbnailUrl(plan.getThumbnailUrl())
        .build())
      .collect(Collectors.toList());
  }
}