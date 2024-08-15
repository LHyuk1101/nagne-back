package com.nagne.domain.plan.repository;

import com.nagne.domain.plan.dto.PlanDto;
import com.nagne.domain.plan.entity.Plan;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanRepository extends JpaRepository<Plan, Long>, PlanRepositoryCustom {

  @Query("SELECT new com.nagne.domain.plan.dto.PlanDto(p.id, p.user.id, p.status, p.startDay, p.endDay, p.thumbnail, a.name, p.subject)"
    + " FROM Plan p"
    + " LEFT JOIN p.area a"
    + " WHERE p.user.id = :userId")
  List<PlanDto> findByUserId(Long userId);

//  @Query("SELECT new com.nagne.domain.plan.dto.PlanDto(p.id,p.user.id, p.status, p.startDay, p.endDay, p.thumbnail, p.subject)"
//    + " FROM Plan p"
//    + " WHERE p.id = :planId")
//  PlanDto findByPlanId(Long planId);
//
//  @Query("SELECT new com.nagne.domain.place.dto.PlaceDTO(p.id, a.name, p.title, p.address, p.contentTypeId, p.overview, COALESCE(s.contactNumber, ''), COALESCE(s.openTime, ''), p.lat, p.lng, p.likes, p.thumbnailUrl, pi.imgUrl) "
//    + "FROM Place p "
//    + "LEFT JOIN Store s on s.place.id = p.id "
//    + "LEFT JOIN Template t on t.place.id = p.id "
//    + "LEFT JOIN PlaceImg pi on pi.place.id = p.id "
//    + "LEFT JOIN Area a on a.areaCode = p.area.areaCode "
//    + "WHERE t.plan.id = :planId")
//  List<PlaceDTO> findByPlanIdForPlaces(Long planId);
}