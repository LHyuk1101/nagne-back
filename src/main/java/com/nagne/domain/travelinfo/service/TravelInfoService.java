package com.nagne.domain.travelinfo.service;

import com.nagne.domain.place.dto.ReqPlaceDto;
import com.nagne.domain.place.dto.ResponsePlaceDto;
import com.nagne.domain.place.entity.Place;
import com.nagne.domain.place.implement.PlaceReader;
import com.nagne.domain.place.repository.PlaceRepository;
import com.nagne.domain.travelinfo.dto.PlaceDTOforTravelInfo;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TravelInfoService {

  private final PlaceReader placeReader;

  private final PlaceRepository placeRepository;

  public ResponsePlaceDto fetchPlaceByAreaName(ReqPlaceDto reqPlaceDto) {
    return placeReader.readPlace(reqPlaceDto);
  }

  public List<PlaceDTOforTravelInfo> findPlaces(Long contentTypeId, Integer areaCode) {
    List<Place> places = placeRepository.findByContentTypeIdAndArea_AreaCode(contentTypeId,
      areaCode);

    return places.stream().map(place ->
      PlaceDTOforTravelInfo.builder()
        .id(place.getId())
        .title(place.getTitle())
        .areaCode(place.getArea().getAreaCode())
        .overview(place.getOverview())
        .build()).collect(Collectors.toList());
  }

  public List<PlaceDTOforTravelInfo> findPlacesByRegion(String region) {
    Pageable pageable = PageRequest.of(0, 10);

    // 두 contentTypeId에 대한 상위 10개의 결과를 각각 가져옴
    List<PlaceDTOforTravelInfo> top10ContentTypeId76 = placeRepository.findTop10ByRegionAndContentTypeId76OrderByLikes(
      region, pageable);
    List<PlaceDTOforTravelInfo> top10ContentTypeId82 = placeRepository.findTop10ByRegionAndContentTypeId82OrderByLikes(
      region, pageable);

    List<PlaceDTOforTravelInfo> combinedResults = new ArrayList<>();
    combinedResults.addAll(top10ContentTypeId76);
    combinedResults.addAll(top10ContentTypeId82);

    return combinedResults;
  }

}
