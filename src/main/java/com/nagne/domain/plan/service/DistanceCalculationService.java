package com.nagne.domain.plan.service;

import com.nagne.domain.place.entity.ContentType;
import com.nagne.domain.place.entity.Place;
import com.nagne.domain.place.repository.PlaceRepository;
import com.nagne.domain.plan.dto.PlanRequestDto;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DistanceCalculationService {
  
  private final PlaceRepository placeRepository;
  
  public DistanceCalculationService(PlaceRepository placeRepository) {
    this.placeRepository = placeRepository;
  }
  
  public List<PlanRequestDto.PlaceDistance> calculateDistances(
    List<PlanRequestDto.PlaceInfo> places) {
    List<Long> placeIds = places.stream().map(PlanRequestDto.PlaceInfo::getId)
      .collect(Collectors.toList());
    List<Place> placeList = placeRepository.findPlaceByPlaceId(placeIds);
    Map<Long, Place> placeMap = placeList.stream()
      .collect(Collectors.toMap(Place::getId, Function.identity()));
    if (places.isEmpty()) {
      throw new IllegalArgumentException("No places selected");
    }
    
    List<PlanRequestDto.PlaceDistance> distances = new ArrayList<>();
    PlanRequestDto.PlaceInfo basePlace = findBasePlace(places);
    Set<Long> visitedPlaces = new HashSet<>();
    visitedPlaces.add(basePlace.getId());
    
    PlanRequestDto.PlaceInfo currentPlace = basePlace;
    
    for (int i = 0; i < places.size() - 1; i++) {
      PlanRequestDto.PlaceInfo nearestPlace = null;
      double nearestDistance = Double.MAX_VALUE;
      
      for (PlanRequestDto.PlaceInfo place : places) {
        if (!visitedPlaces.contains(place.getId())) {
          double distance = calculateDistance(currentPlace.getId(), place.getId());
          
          if (distance < nearestDistance) {
            nearestDistance = distance;
            nearestPlace = place;
          }
        }
      }
      
      if (nearestPlace != null) {
        distances.add(PlanRequestDto.PlaceDistance.builder()
          .fromPlaceId(currentPlace.getId())
          .toPlaceId(nearestPlace.getId())
          .distance(nearestDistance)
          .build());
        visitedPlaces.add(nearestPlace.getId());
        currentPlace = nearestPlace;
      }
    }
    
    return distances;
  }
  
  private PlanRequestDto.PlaceInfo findBasePlace(List<PlanRequestDto.PlaceInfo> places) {
    Optional<PlanRequestDto.PlaceInfo> accommodation = places.stream()
      .filter(place -> {
        Place placeEntity = placeRepository.findById(place.getId())
          .orElseThrow(
            () -> new IllegalArgumentException("Place not found with id: " + place.getId()));
        return placeEntity.getContentTypeId().equals(ContentType.B.getType());
      })
      .findFirst();
    
    if (accommodation.isPresent()) {
      return accommodation.get();
    } else {
      PlanRequestDto.PlaceInfo firstPlace = places.get(0);
      return firstPlace;
    }
  }
  
  private double calculateDistance(Long placeId1, Long placeId2) {
    Place place1 = placeRepository.findById(placeId1)
      .orElseThrow(() -> new IllegalArgumentException("Place not found with id: " + placeId1));
    Place place2 = placeRepository.findById(placeId2)
      .orElseThrow(() -> new IllegalArgumentException("Place not found with id: " + placeId2));
    
    final int R = 6371; // 지구의 반지름 (km)
    
    double latDistance = Math.toRadians(place2.getLat() - place1.getLat());
    double lonDistance = Math.toRadians(place2.getLng() - place1.getLng());
    double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
      + Math.cos(Math.toRadians(place1.getLat())) * Math.cos(Math.toRadians(place2.getLat()))
      * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    double distance = R * c;
    
    return Math.round(distance * 10.0) / 10.0; // 소수점 첫번째 자리까지 반올림.
  }
}
