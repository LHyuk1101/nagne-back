package com.nagne.domain.plan.controller;

import com.nagne.domain.plan.dto.PlanDto;
import com.nagne.domain.plan.service.PlanService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/plans")
public class PlanController {
  
  @Autowired
  private PlanService planService;
  
  @GetMapping
  public ResponseEntity<List<PlanDto>> getAllPlans() {
    List<PlanDto> plans = planService.getAllPlans();
    return ResponseEntity.ok(plans);
  }
  
  @GetMapping("/{id}")
  public ResponseEntity<PlanDto> getPlanById(@PathVariable Long id) {
    PlanDto plan = planService.getPlanById(id);
    return plan != null ? ResponseEntity.ok(plan) : ResponseEntity.notFound().build();
  }
}