package com.nagne.domain.travelinfo.controller;

import com.nagne.domain.place.dto.PlaceDTO;
import com.nagne.domain.place.dto.ReqPlaceDto;
import com.nagne.domain.place.dto.ResponsePlaceDto;
import com.nagne.domain.place.repository.PlaceRepository;
import com.nagne.domain.travelinfo.dto.PlaceDTOforTravelInfo;
import com.nagne.domain.travelinfo.service.TravelInfoService;
import com.nagne.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/place")
@Tag(name = "Response Places data", description = "Place 테이블에 저장된 데이터를 각 기준별로 호출하는 API 입니다.")
public class TravelInfoController {
  
  private final PlaceRepository placeRepository;
  private final TravelInfoService travelInfoService;
  
  // 특정 ID의 Place 정보를 가져오는 API 엔드포인트
  @GetMapping("/details/{id}")
  @Operation(summary = "상세페이지접근시 URL의 ID를 기준으로 정보 조회", description = "URL의 ID를 기준으로 정보를 보여주어 해당 장소 ID가 담긴 URL을 공유할 수 있게하는 API")
  public ApiResponse<PlaceDTO> getPlaceDetails(@PathVariable Long id) {
    PlaceDTO placeDTO = travelInfoService.getPlaceById(id);
    return ApiResponse.success(placeDTO);
  }
  
  @GetMapping("/search")
  @Operation(summary = "지역 코드와 키워드로 장소 검색", description = "지역 코드와 검색 키워드로 장소를 검색하는 API")
  public ApiResponse<List<PlaceDTO>> searchPlacesByRegionAndKeyword(
    @RequestParam("areaCode") int areaCode,
    @RequestParam("keyword") String keyword) {
    List<PlaceDTO> searchResults = travelInfoService.searchPlacesByRegionAndKeyword(areaCode,
      keyword);
    return ApiResponse.success(searchResults);
  }
  
  
  @GetMapping("/find/{areaCode}")
  @Operation(summary = "지역 코드별 장소를 추출", description = "서울(1)이면 서울의 식당과, 관광지 데이터를 좋아요 기준 상위10건의 데이터를 반환받는 API")
  public ApiResponse<List<PlaceDTO>> findPlacesByRegion(
    @PathVariable("areaCode") int areaCode) {
    List<PlaceDTO> placesByRegion = travelInfoService.findPlacesByRegion(areaCode);
    return ApiResponse.success(placesByRegion);
  }
  
  @GetMapping("/find/{contentTypeId}/{areaCode}")
  @Operation(summary = "지역 코드와 각 데이터들이 가지는 contentTypeId별로 장소를 추출", description = "관광지(76)를 서울(1)에 대해서 반환 받을 수 있는 API")
  public ApiResponse<List<PlaceDTOforTravelInfo>> findPlaces(@PathVariable Long contentTypeId,
    @PathVariable Integer areaCode) {
    List<PlaceDTOforTravelInfo> result = travelInfoService.findPlaces(contentTypeId, areaCode);
    return ApiResponse.success(result);
  }
  
  @GetMapping("/travel")
  @Operation(summary = "사용하지 않음")
  public ApiResponse<ResponsePlaceDto> getPlaceById(@ModelAttribute ReqPlaceDto reqPlaceDto) {
    ResponsePlaceDto places = travelInfoService.fetchPlaceByAreaName(reqPlaceDto);
    return ApiResponse.success(places);
  }
  
  @GetMapping("/findall/{areaCode}")
  @Operation(summary = "지역 코드로 데이터를 모두 추출", description = "서울(1)의 관광지,숙소,식당 등 모든데이터를 추출해오는 API")
  public ApiResponse<List<PlaceDTOforTravelInfo>> findAllPlacesByRegion(
    @PathVariable("areaCode") int areaCode) {
    List<PlaceDTOforTravelInfo> allPlacesByRegion = placeRepository.findAllPlacesByRegion(areaCode);
    return ApiResponse.success(allPlacesByRegion);
    
  }
  
  
}

