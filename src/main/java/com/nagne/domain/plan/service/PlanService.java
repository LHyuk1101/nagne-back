package com.nagne.domain.plan.service;

import com.nagne.domain.place.dto.PlaceDTO;
import com.nagne.domain.plan.dto.PlanDto;
import com.nagne.domain.plan.repository.PlanRepository;
import com.nagne.domain.template.repository.TemplateRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlanService {

  private static final Logger log = LoggerFactory.getLogger(PlanService.class);
  private final PlanRepository planRepository;
  private final TemplateRepository templateRepository;

  public List<PlanDto> getAllPlans() {
    return null; //Todo: 모든 플랜 조회가 어디서 필요하지?
  }

  public PlanDto getPlanById(Long planId) {
    PlanDto planDto = null;
    try{
      planDto = planRepository.findByPlanId(planId);
      List<PlaceDTO> placeDTOS = planRepository.findByPlanIdForPlaces(planId);
      planDto.setPlaceDTOs(placeDTOS);
    }
    catch(Exception e){
      log.error(e.getMessage());
    }
    return planDto;
  }
}