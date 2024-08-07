package com.nagne.domain.plan.service;

import com.nagne.domain.plan.dto.PlanDto;
import com.nagne.domain.plan.entity.Plan;
import com.nagne.domain.plan.repository.PlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlanService {

    @Autowired
    private PlanRepository planRepository;

    public List<PlanDto> getAllPlans() {
        return planRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public PlanDto getPlanById(Long id) {
        return planRepository.findById(id).map(this::convertToDTO).orElse(null);
    }

    private PlanDto convertToDTO(Plan plan) {
        return new PlanDto(plan.getId(), plan.getUser().getId(), plan.getStatus().name(), plan.getStartDay(), plan.getEndDay(), plan.getAreaCode(), plan.getLabel());
    }
}