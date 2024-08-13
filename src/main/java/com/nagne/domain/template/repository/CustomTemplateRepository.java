package com.nagne.domain.template.repository;

import com.nagne.domain.template.entity.Template;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomTemplateRepository extends JpaRepository<Template, Long> {

  @Query("SELECT t FROM Template t JOIN FETCH t.place p JOIN FETCH t.plan pl WHERE pl.area.areaCode = :areaCode")
  List<Template> findCustomTemplateByAreaCode(@Param("areaCode") int areaCode);
}