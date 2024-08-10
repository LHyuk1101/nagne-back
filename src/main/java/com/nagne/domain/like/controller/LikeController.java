package com.nagne.domain.like.controller;

import com.nagne.domain.place.dto.PlaceDTO;
import com.nagne.domain.place.entity.Place;
import com.nagne.domain.place.repository.PlaceRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/place")
public class LikeController {

  private final PlaceRepository placeRepository;

  @GetMapping("/find/{contentTypeId}/{areaCode}")
  public List<PlaceDTO> findPlaces(@PathVariable Long contentTypeId,
      @PathVariable Integer areaCode) {

    List<Place> places = placeRepository.findByContentTypeIdAndArea_AreaCode(contentTypeId,
        areaCode);

    return places.stream().map(place ->
        PlaceDTO.builder()
            .id(place.getId())
            .name(place.getTitle())
            .areaCode(place.getArea().getAreaCode())
            .overview(place.getOverview())
            .build()).collect(Collectors.toList());
  }

  @GetMapping("/find/{areaCode}")
  public List<PlaceDTO> findPlaecsByAreaCode(@PathVariable Integer areaCode) {

    List<Place> places = placeRepository.findByArea_AreaCode(areaCode);

    return places.stream().map(place -> PlaceDTO.builder()
        .id(place.getId())
        .name(place.getTitle())
        .areaCode(place.getArea().getAreaCode())
        .overview(place.getOverview())
        .address(place.getAddress())
        .infocenter("031-123-123")
        .build()).collect(Collectors.toList());
  }

  @GetMapping("/findall")
  public List<PlaceDTO> findAllPlaces() {
    List<Place> places = placeRepository.findAll();

    return places.stream().map(place -> PlaceDTO.builder()
        .id(place.getId())
        .name(place.getTitle())
        .areaCode(place.getArea().getAreaCode())
        .overview(place.getOverview())
        .address(place.getAddress())
        .infocenter("031-123-123")
        .contentTypeId(place.getContentTypeId())
        .build()).collect(Collectors.toList());
  }
}
