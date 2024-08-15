package com.nagne.domain.travelinfo.service;

import com.nagne.domain.place.dto.PlaceDTO;
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

  public List<PlaceDTO> searchPlacesByRegionAndKeyword(int areaCode, String keyword) {
    return placeRepository.searchPlacesByRegionAndKeyword(areaCode, keyword);
  }

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

  public List<PlaceDTO> findPlacesByRegion(int areaCode) {
    Pageable pageable = PageRequest.of(0, 10);

    // contentTypeId 76의 상위 10개 데이터 가져오기
    List<PlaceDTO> top10ContentTypeId76 = placeRepository.findTopLikesByRegion(
      new Long[]{76L}, areaCode, pageable);

    // contentTypeId 82의 상위 10개 데이터 가져오기
    List<PlaceDTO> top10ContentTypeId82 = placeRepository.findTopLikesByRegion(
      new Long[]{82L}, areaCode, pageable);

    // 쌈@뽕한 병합
    List<PlaceDTO> combinedResults = new ArrayList<>();

    combinedResults.addAll(top10ContentTypeId76);
    combinedResults.addAll(top10ContentTypeId82);

    return combinedResults;
  }


}
