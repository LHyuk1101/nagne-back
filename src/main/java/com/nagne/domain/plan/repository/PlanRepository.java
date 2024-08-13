package com.nagne.domain.plan.repository;

import com.nagne.domain.plan.dto.PlanDto;
import com.nagne.domain.plan.entity.Plan;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanRepository extends JpaRepository<Plan, Long> {

  @Query("SELECT new com.nagne.domain.plan.dto.PlanDto(p.id, p.user.id, p.status, p.startDay, p.endDay, p.thumbnailUrl, a.name, p.subject)"
    + " FROM Plan p"
    + " JOIN p.area a"
    + " WHERE p.user.id = :userId")
  List<PlanDto> findByUserId(Long userId);
}