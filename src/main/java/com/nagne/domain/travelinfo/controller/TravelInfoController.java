package com.nagne.domain.travelinfo.controller;

import com.nagne.domain.place.dto.ReqPlaceDto;
import com.nagne.domain.place.dto.ResponsePlaceDto;
import com.nagne.domain.place.repository.PlaceRepository;
import com.nagne.domain.travelinfo.dto.PlaceDTOforTravelInfo;
import com.nagne.domain.travelinfo.service.TravelInfoService;
import com.nagne.global.response.ApiResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
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
  public ApiResponse<List<PlaceDTOforTravelInfo>> findPlacesByRegion(
    @PathVariable("region") String region) {
    List<PlaceDTOforTravelInfo> combinedResults = travelInfoService.findPlacesByRegion(region);
    return ApiResponse.success(combinedResults);
  }

  @GetMapping("/find/{contentTypeId}/{areaCode}")
  public ApiResponse<List<PlaceDTOforTravelInfo>> findPlaces(@PathVariable Long contentTypeId,
    @PathVariable Integer areaCode) {
    List<PlaceDTOforTravelInfo> result = travelInfoService.findPlaces(contentTypeId, areaCode);

    return ApiResponse.success(result);
  }


  @GetMapping("/travel")
  public ApiResponse<ResponsePlaceDto> getPlaceById(@ModelAttribute ReqPlaceDto reqPlaceDto) {
    ResponsePlaceDto places = travelInfoService.fetchPlaceByAreaName(reqPlaceDto);
    return ApiResponse.success(places);
  }


  @GetMapping("/findall/{region}")
  public List<PlaceDTOforTravelInfo> findAllPlacesByRegion(@PathVariable("region") String region) {
    // findAllPlacesByRegion 메서드를 호출하여 모든 데이터를 가져옴
    return placeRepository.findAllPlacesByRegion(region);
  }


}

