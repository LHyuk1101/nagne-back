package com.nagne.domain.place.repository;

import com.nagne.domain.place.entity.Place;
import java.util.List;
import org.springframework.data.domain.Pageable;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PlaceRepository extends JpaRepository<Place, Long> {


  @Query("SELECT p "
    + "FROM Place p "
    + "JOIN FETCH p.placeImgs "
    + "WHERE p.contentTypeId IN :regionIds "
    + "ORDER BY p.likes, p.id")
  List<Place> findByRegion(@Param("regionIds") Long[] regionIds, Pageable pageable);

  Optional<Place> findByTitle(String title);

}
