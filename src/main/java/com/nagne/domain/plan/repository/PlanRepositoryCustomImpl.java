package com.nagne.domain.plan.repository;

import com.nagne.domain.place.entity.QPlace;
import com.nagne.domain.place.entity.QPlaceImg;
import com.nagne.domain.place.entity.QStore;
import com.nagne.domain.plan.dto.PlanResponseDto;
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
    this.queryFactory = new JPAQueryFactory(em);;
  }

  @Override
  public PlanResponseDto findByPlanId(Long planId) {
    QTemplate t = QTemplate.template;
    QPlan plan = QPlan.plan;
    QPlace p = QPlace.place;
    QStore s = QStore.store;
    QPlaceImg pi = QPlaceImg.placeImg;

    // Plan 정보 추출
    PlanResponseDto planResponseDto = queryFactory
      .select(Projections.constructor(
        PlanResponseDto.class,
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

    List<PlanResponseDto.DayPlan> dayPlans = templateDtoList.stream()
      .collect(Collectors.groupingBy(
        TemplateDto::getDays,
        LinkedHashMap::new,
        Collectors.mapping(dto -> PlanResponseDto.PlaceDetail.builder()
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
      .map(entry -> PlanResponseDto.DayPlan.builder()
        .day(entry.getKey())
        .places(entry.getValue())
        .build())
      .collect(Collectors.toList());

    return PlanResponseDto.builder()
      .id(planResponseDto.getId())
      .userId(planResponseDto.getUserId())
      .status(planResponseDto.getStatus())
      .startDay(planResponseDto.getStartDay())
      .endDay(planResponseDto.getEndDay())
      .areaCode(planResponseDto.getAreaCode())
      .areaCodeName(planResponseDto.getAreaCodeName())
      .subject(planResponseDto.getSubject())
      .type(planResponseDto.getType())
      .thumbnailUrl(planResponseDto.getThumbnailUrl())
      .dayPlans(dayPlans)
      .build();
  }
}
