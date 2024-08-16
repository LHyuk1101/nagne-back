package com.nagne.domain.plan.repository;

import com.nagne.domain.plan.dto.PlanUserResponseDto;

public interface PlanRepositoryCustom {

  PlanUserResponseDto findByPlanId(Long planId);
}
