package com.nagne.domain.travelinfo.controller;

import com.nagne.domain.place.dto.PlaceDTO;
import com.nagne.domain.place.dto.ReqPlaceDto;
import com.nagne.domain.place.entity.Place;
import com.nagne.domain.place.repository.PlaceRepository;
import com.nagne.domain.travelinfo.dto.PlaceDTOforTravelInfo;
import com.nagne.domain.travelinfo.service.TravelInfoService;
import com.nagne.global.response.ApiResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/place")
public class TravelInfoController {

  private final PlaceRepository placeRepository;
  private final TravelInfoService travelInfoService;

  @GetMapping("/find/{region}")
  public List<PlaceDTOforTravelInfo> findPlacesByRegion(@PathVariable("region") String region) {

    // 각 contentTypeId에 대해 10건씩 가져오기 위한 Pageable 설정
    Pageable pageable = PageRequest.of(0, 10);

    // 두 contentTypeId에 대한 상위 10개의 결과를 각각 가져옴
    List<PlaceDTOforTravelInfo> top10ContentTypeId76 = placeRepository.findTop10ByRegionAndContentTypeId76OrderByLikes(
      region, pageable);
    List<PlaceDTOforTravelInfo> top10ContentTypeId82 = placeRepository.findTop10ByRegionAndContentTypeId82OrderByLikes(
      region, pageable);

    // 결과를 합침
    List<PlaceDTOforTravelInfo> combinedResults = new ArrayList<>();
    combinedResults.addAll(top10ContentTypeId76);
    combinedResults.addAll(top10ContentTypeId82);

    return combinedResults;

  }


  @GetMapping("/travel")
  public ApiResponse<List<PlaceDTO>> getPlaceById(@ModelAttribute ReqPlaceDto reqPlaceDto) {
    List<PlaceDTO> places = travelInfoService.fetchPlaceByAreaName(reqPlaceDto);
    return ApiResponse.success(places);
  }

  @GetMapping("/findall")
  @Transactional(readOnly = true)
  public List<PlaceDTO> findAllPlaces() {
    List<Place> places = placeRepository.findAll();

    return places.stream().map(place -> PlaceDTO.builder()
      .id(place.getId())
      .title(place.getTitle())
      .areaCode(place.getArea().getAreaCode())
      .overview(place.getOverview())
      .address(place.getAddress())
      .contactNumber("031-123-123")
      .contentTypeId(place.getContentTypeId())
      .build()).collect(Collectors.toList());
  }


  @GetMapping("/find/{contentTypeId}/{areaCode}")
  public List<PlaceDTO> findPlaces(@PathVariable Long contentTypeId,
    @PathVariable Integer areaCode) {

    List<Place> places = placeRepository.findByContentTypeIdAndArea_AreaCode(contentTypeId,
      areaCode);

    return places.stream().map(place ->
      PlaceDTO.builder()
        .id(place.getId())
        .title(place.getTitle())
        .areaCode(place.getArea().getAreaCode())
        .overview(place.getOverview())
        .build()).collect(Collectors.toList());

  }
//
//  @GetMapping("/find/{areaCode}")
//  @Transactional(readOnly = true)
//  public List<PlaceDTO> findPlaecsByAreaCode(@PathVariable Integer areaCode) {
//
//    List<Place> places = placeRepository.findByArea_AreaCode(areaCode);
//
//    return places.stream().map(place -> PlaceDTO.builder()
//
//      .id(place.getId())
//      .title(place.getTitle())
//      .area(place.getArea())
//      .contentTypeId(place.getContentTypeId())
//      .overview(place.getOverview())
//      .address(place.getAddress())
//      .contactNumber("031-123-123")
//      .build()).collect(Collectors.toList());
//  }

}
