package com.nagne.domain.plan.repository;

import com.nagne.domain.plan.dto.PlanResponseDto;

public interface PlanRepositoryCustom {
  
  PlanResponseDto findByPlanId(Long planId);
}
