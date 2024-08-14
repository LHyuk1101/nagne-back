package com.nagne.domain.place.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ResponsePlaceDto {
  
  private List<PlaceDTO> placeList;
  
  private int totalCount;
  
}
