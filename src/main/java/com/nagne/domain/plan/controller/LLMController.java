package com.nagne.domain.plan.controller;

import com.nagne.domain.plan.dto.PlanRequestDto;
import com.nagne.domain.plan.dto.PlanResponseDto;
import com.nagne.domain.plan.service.LLMService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

@RestController
@RequestMapping("/api/llm")
public class LLMController {
  
  private static final Logger log = LoggerFactory.getLogger(LLMController.class);
  
  private final LLMService llmService;
  
  public LLMController(LLMService llmService) {
    this.llmService = llmService;
  }
  
  @PostMapping("/create-plan")
  public DeferredResult<ResponseEntity<PlanResponseDto>> generatePlan(
    @RequestBody PlanRequestDto request) {
    Long userId = request.getUserId();  // 요청하고 userId를 받아옴
    DeferredResult<ResponseEntity<PlanResponseDto>> deferredResult = new DeferredResult<>(60000L);
    llmService.generateAndSavePlan(request, userId)
      .thenApply(ResponseEntity::ok)
      .exceptionally(ex -> {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
      })
      .thenAccept(deferredResult::setResult);
    deferredResult.onTimeout(() -> deferredResult.setErrorResult(
      ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT).body("Request timeout occurred.")));
    return deferredResult;
  }
  
}

