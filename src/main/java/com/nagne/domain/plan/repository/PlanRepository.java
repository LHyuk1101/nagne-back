package com.nagne.domain.plan.repository;

import com.nagne.domain.plan.entity.Plan;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanRepository extends JpaRepository<Plan, Long> {

  List<Plan> findByArea_AreaCode(Integer areaCode);
}