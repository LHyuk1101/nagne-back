package com.nagne.domain.place.service;

import com.nagne.domain.place.dto.ReqPlaceDto;
import com.nagne.domain.place.dto.ResponsePlaceDto;
import com.nagne.domain.place.implement.PlaceReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlaceService {

  private final PlaceReader placeReader;

  public ResponsePlaceDto fetchPlaceByRegion(ReqPlaceDto reqPlaceDto) {

    return placeReader.readPlace(reqPlaceDto);
  }


}
