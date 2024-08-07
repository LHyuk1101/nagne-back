package com.nagne.domain.place.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PlaceDTO {

  private Long id;
  private String name;
  private int areaCode;
  private String overview;
}
