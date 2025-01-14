package com.nagne.domain.plan.service;

import com.nagne.domain.plan.dto.PlanDto;
import com.nagne.domain.plan.dto.PlanUserResponseDto;
import com.nagne.domain.plan.repository.PlanRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class PlanService {
  
  private final PlanRepository planRepository;
  
  public List<PlanDto> getAllPlans() {
    return null; //Todo: 모든 플랜 조회가 어디서 필요하지?
  }
  
  public PlanUserResponseDto getPlanById(Long planId) {
    PlanUserResponseDto planUserResponseDto = null;
    try {
      planUserResponseDto = planRepository.findByPlanId(planId);
    } catch (Exception e) {
      log.error(e.getMessage());
    }
    return planUserResponseDto;
  }
}