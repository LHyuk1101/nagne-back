package com.nagne.domain.place.service;

import com.nagne.domain.place.dto.PlaceDTO;
import com.nagne.domain.place.entity.Place;
import com.nagne.domain.place.repository.PlaceRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PlaceService {

  private final PlaceRepository placeRepository;

  @Transactional
  public List<PlaceDTO> fetchPlaceData(){
    List<Place> data = placeRepository.findAll();

    List<PlaceDTO> dtos = new ArrayList<>();
    for(Place p : data){
      dtos.add(PlaceDTO.builder()
              .id(p.getId())
              .areaCode(p.getArea().getAreaCode())
              .overview(p.getOverview())
          .build());
    }
    return dtos;
  }
}
