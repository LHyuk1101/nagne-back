package com.nagne.domain.template.repository;

import com.nagne.domain.place.entity.Place;
import com.nagne.domain.template.entity.Template;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TemplateRepository extends JpaRepository<Template, Long> {
  
  @Query("SELECT p FROM Place p JOIN Template t ON p.id = t.place.id WHERE t.plan.id = :planId")
  List<Place> findAllByPlanId(@Param("planId") Long planId);
  
  @Query("SELECT t FROM Template t JOIN FETCH t.place WHERE t.plan.id = :planId ORDER BY t.day, t.order")
  List<Template> findAllByPlanIdWithPlace(@Param("planId") Long planId);
}
