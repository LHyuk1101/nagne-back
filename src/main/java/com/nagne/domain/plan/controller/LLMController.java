package com.nagne.domain.plan.controller;

import com.nagne.domain.plan.dto.PlanRequestDto;
import com.nagne.domain.plan.dto.PlanResponseDto;
import com.nagne.domain.plan.service.LLMService;
import java.util.concurrent.CompletableFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/llm")
public class LLMController {
  
  private static final Logger log = LoggerFactory.getLogger(LLMController.class);
  
  private final LLMService llmService;
  
  public LLMController(LLMService llmService) {
    this.llmService = llmService;
  }
  
  @PostMapping("/create-plan")
  public CompletableFuture<ResponseEntity<PlanResponseDto>> generatePlan(
    @RequestBody PlanRequestDto request) {
    
    Long userId = request.getUserId();  // 요청하고 userId를 받아옴
    return llmService.generateAndSavePlan(request, userId)
      .thenApply(ResponseEntity::ok)
      .exceptionally(ex -> {
        log.error("Error generating plan", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
      });
  }
}
