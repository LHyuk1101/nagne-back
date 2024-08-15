package com.nagne.domain.place.controller;

import com.nagne.domain.place.dto.ReqPlaceDto;
import com.nagne.domain.place.dto.ResponsePlaceDto;
import com.nagne.domain.place.service.PlaceService;
import com.nagne.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/place")
@Tag(name = "PlaceController")
public class PlaceController {
  
  private final PlaceService placeService;
  
  @GetMapping
  @Operation(summary = "RequestDto: Regions, areaCode, page, size", description = "지역에 따른 관광지 등등 조건 타입의 데이터를 가져옵니다.")
  public ApiResponse<ResponsePlaceDto> getPlaceById(@ModelAttribute ReqPlaceDto reqPlaceDto) {
    ResponsePlaceDto places = placeService.fetchPlaceByRegion(reqPlaceDto);
    return ApiResponse.success(places);
  }
  
}
