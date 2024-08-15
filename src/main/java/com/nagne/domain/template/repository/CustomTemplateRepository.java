package com.nagne.domain.template.repository;

import com.nagne.domain.template.dto.CustomTemplateDto;
import com.nagne.domain.template.entity.Template;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomTemplateRepository extends JpaRepository<Template, Long> {


  @Query(
    "SELECT DISTINCT new com.nagne.domain.template.dto.CustomTemplateDto(t.plan.id, t.plan.subject, t.plan.overview, t.plan.thumbnail, t.plan.area.areaCode) "
      + "FROM Template t "
      + "JOIN t.plan p "
      + "WHERE p.area.areaCode = :areaCode "
      + "GROUP BY p.id "
      + "ORDER BY p.id")
  List<CustomTemplateDto> findCustomTemplateByAreaCode(@Param("areaCode") int areaCode);

}