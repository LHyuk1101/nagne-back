package com.nagne.domain.place.controller;

import com.nagne.domain.place.dto.PlaceDTO;
import com.nagne.domain.place.dto.ReqPlaceDto;
import com.nagne.domain.place.service.PlaceService;
import com.nagne.global.response.ApiResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/place")
public class PlaceController {

  private final PlaceService placeService;

  @GetMapping
  public ApiResponse<List<PlaceDTO>> getPlaceById(@ModelAttribute ReqPlaceDto reqPlaceDto) {
    List<PlaceDTO> places = placeService.fetchPlaceByRegion(reqPlaceDto);
    return ApiResponse.success(places);
  }

}
