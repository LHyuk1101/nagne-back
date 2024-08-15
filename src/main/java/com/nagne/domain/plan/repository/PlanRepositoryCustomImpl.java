package com.nagne.domain.plan.repository;

import com.nagne.domain.place.entity.QPlace;
import com.nagne.domain.place.entity.QPlaceImg;
import com.nagne.domain.place.entity.QStore;
import com.nagne.domain.plan.dto.PlanUserResponseDto;
import com.nagne.domain.plan.entity.QPlan;
import com.nagne.domain.template.dto.TemplateDto;
import com.nagne.domain.template.entity.QTemplate;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

public class PlanRepositoryCustomImpl implements PlanRepositoryCustom {

  private final JPAQueryFactory queryFactory;

  public PlanRepositoryCustomImpl(EntityManager em) {
    this.queryFactory = new JPAQueryFactory(em);
    ;
  }

  @Override
  public PlanUserResponseDto findByPlanId(Long planId) {
    QTemplate t = QTemplate.template;
    QPlan plan = QPlan.plan;
    QPlace p = QPlace.place;
    QStore s = QStore.store;
    QPlaceImg pi = QPlaceImg.placeImg;

    // Plan 정보 추출
    PlanUserResponseDto planUserResponseDto = queryFactory
      .select(Projections.constructor(
        PlanUserResponseDto.class,
        plan.id,
        plan.user.id,
        plan.status,
        plan.startDay,
        plan.endDay,
        plan.area.areaCode,
        plan.area.name,
        plan.subject,
        plan.type,
        plan.thumbnail
      ))
      .distinct()
      .from(plan)
      .where(plan.id.eq(planId))
      .fetchFirst();

    // TemplateDto 목록을 추출하여 처리
    List<TemplateDto> templateDtoList = queryFactory
      .select(Projections.constructor(
        TemplateDto.class,
        t.id,
        t.day,
        t.order,
        t.moveTime,
        p.id,
        p.title,
        p.contentTypeId,
        t.placeSummary,
        t.reasoning,
        p.thumbnailUrl
      ))
      .distinct()
      .from(t)
      .leftJoin(t.place, p)
      .leftJoin(s).on(s.place.id.eq(p.id))
      .leftJoin(p.placeImgs, pi)
      .where(t.plan.id.eq(planId))
      .orderBy(t.day.asc(), t.order.asc())
      .fetch();

    List<PlanUserResponseDto.DayPlan> dayPlans = templateDtoList.stream()
      .collect(Collectors.groupingBy(
        TemplateDto::getDays,
        LinkedHashMap::new,
        Collectors.mapping(dto -> PlanUserResponseDto.PlaceDetail.builder()
            .placeId(dto.getPlaceId())
            .title(dto.getTitle())
            .contentType(dto.getContentType().toString())
            .order(dto.getOrder())
            .moveTime(dto.getMoveTime())
            .placeSummary(dto.getPlaceSummary())
            .reasoning(dto.getReasoning())
            .placeImgUrls(dto.getPlaceImgUrls())
            .build(),
          Collectors.toList())
      ))
      .entrySet().stream()
      .map(entry -> PlanUserResponseDto.DayPlan.builder()
        .day(entry.getKey())
        .places(entry.getValue())
        .build())
      .collect(Collectors.toList());

    return PlanUserResponseDto.builder()
      .id(planUserResponseDto.getId())
      .userId(planUserResponseDto.getUserId())
      .status(planUserResponseDto.getStatus())
      .startDay(planUserResponseDto.getStartDay())
      .endDay(planUserResponseDto.getEndDay())
      .areaCode(planUserResponseDto.getAreaCode())
      .areaCodeName(planUserResponseDto.getAreaCodeName())
      .subject(planUserResponseDto.getSubject())
      .type(planUserResponseDto.getType())
      .thumbnailUrl(planUserResponseDto.getThumbnailUrl())
      .dayPlans(dayPlans)
      .build();
  }
}
