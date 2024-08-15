package com.nagne.domain.place.repository;

import com.nagne.domain.place.dto.PlaceDTO;
import com.nagne.domain.place.entity.QPlace;
import com.nagne.domain.place.entity.QPlaceImg;
import com.nagne.domain.place.entity.QStore;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

public class PlaceRepositoryCustomImpl implements PlaceRepositoryCustom {

  private final JPAQueryFactory queryFactory;

  public PlaceRepositoryCustomImpl(EntityManager em) {
    this.queryFactory = new JPAQueryFactory(em);
  }

  @Override
  public List<PlaceDTO> findByRegionAndSearchTerm(Long[] regionIds, int areaCode, String searchTerm,
    Pageable pageable) {
    QPlace p = QPlace.place;
    QStore s = QStore.store;
    QPlaceImg pi = QPlaceImg.placeImg;

    return queryFactory
      .select(Projections.constructor(PlaceDTO.class,
        p.id,
        p.area,
        p.title,
        p.address,
        p.contentTypeId,
        p.overview,
        s.contactNumber.coalesce(""),
        s.openTime.coalesce(""),
        p.lat,
        p.lng,
        p.likes,
        p.thumbnailUrl,
        pi.imgUrl
      ))
      .distinct()
      .from(p)
      .leftJoin(s).on(s.place.id.eq(p.id))
      .leftJoin(p.placeImgs, pi)
      .where(
        p.contentTypeId.in(regionIds),
        p.area.areaCode.eq(areaCode),
        searchTermContains(searchTerm)
      )
      .orderBy(p.likes.desc(), p.id.asc())
      .offset(pageable.getOffset())
      .limit(pageable.getPageSize())
      .fetch();

  }

  private BooleanExpression searchTermContains(String searchTerm) {
    return StringUtils.hasText(searchTerm) ?
      QPlace.place.title.containsIgnoreCase(searchTerm) : null;
  }
}
