package com.nagne.domain.plan.controller;

import com.nagne.domain.plan.dto.PlanRequestDto;
import com.nagne.domain.plan.entity.Plan;
import com.nagne.domain.plan.service.LLMService;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/llm")
public class LLMController {
  
  private final LLMService llmService;
  
  public LLMController(LLMService llmService) {
    this.llmService = llmService;
  }
  
  @PostMapping("/create-plan")
  public CompletableFuture<ResponseEntity<List<Plan>>> generatePlans(
    @RequestBody PlanRequestDto request) {
    return llmService.generateAndSavePlans(request).thenApply(ResponseEntity::ok)
      .exceptionally(ex -> {
        // Log the exception
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
      });
  }
}
