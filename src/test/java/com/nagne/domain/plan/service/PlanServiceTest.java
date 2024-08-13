package com.nagne.domain.plan.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.nagne.domain.plan.dto.PlanDto;
import com.nagne.domain.plan.entity.Plan;
import com.nagne.domain.plan.entity.Plan.Status;
import com.nagne.domain.plan.repository.PlanRepository;
import com.nagne.domain.user.entity.User;
import com.nagne.domain.user.entity.UserRole;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class PlanServiceTest {
  
  private static final Logger logger = LoggerFactory.getLogger(PlanServiceTest.class);
  
  @Mock
  private PlanRepository planRepository;
  
  @InjectMocks
  private PlanService planService;
  
  private User user;
  private Plan plan;
  
  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    user = User.builder()
      .id(1L)
      .email("test@example.com")
      .nickname("testuser")
      .role(UserRole.USER)
      .build();
    
    plan = Plan.builder()
      .id(1L)
      .user(user)
      .status(Status.BEGIN)
      .startDay(LocalDate.of(2023, 1, 1))
      .endDay(LocalDate.of(2023, 1, 7))
      .build();
  }
  
  @Test
  void testGetAllPlans() {
    when(planRepository.findAll()).thenReturn(Collections.singletonList(plan));
    
    logger.info("Fetching all plans");
    
    List<PlanDto> plans = planService.getAllPlans();
    
    // Log the fetched plans and their duration
    logger.info("Fetched plans: {}", plans);
    plans.forEach(p -> logger.info("Plan ID: {}, Duration: {}", p.getId(), p.getDuration()));
    
    assertNotNull(plans);
    assertEquals(1, plans.size());
    assertEquals(7, plans.get(0).getDuration());
    verify(planRepository, times(1)).findAll();
  }
  
  @Test
  void testGetPlanById() {
    when(planRepository.findById(1L)).thenReturn(Optional.of(plan));
    
    logger.info("Fetching plan by ID: 1");
    
    PlanDto planDto = planService.getPlanById(1L);
    
    // Log the fetched plan and its duration
    logger.info("Fetched plan: {}", planDto);
    if (planDto != null) {
      logger.info("Plan ID: {}, Duration: {}", planDto.getId(), planDto.getDuration());
    }
    
    assertNotNull(planDto);
    assertEquals(7, planDto.getDuration());
    verify(planRepository, times(1)).findById(1L);
  }
  
  @Test
  void testGetPlanById_NotFound() {
    when(planRepository.findById(1L)).thenReturn(Optional.empty());
    
    logger.info("Fetching plan by ID that does not exist: 1");
    
    PlanDto planDto = planService.getPlanById(1L);
    
    // Log the result of fetching a non-existing plan
    logger.info("Result of fetching plan: {}", planDto);
    
    assertNull(planDto);
    verify(planRepository, times(1)).findById(1L);
  }
}