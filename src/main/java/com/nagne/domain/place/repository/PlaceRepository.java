package com.nagne.domain.place.repository;

import com.nagne.domain.place.entity.Place;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PlaceRepository extends JpaRepository<Place, Long> {

    @Query("SELECT p FROM Place p JOIN Template t ON p.id = t.place.id WHERE t.plan.id = :planId")
    List<Place> findAllByPlanId(@Param("planId") Long planId);
    Optional<Place> findByTitle(String title);

}