package com.nagne.domain.place.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReqPlaceDto {

  private String[] regions;
  private int areaCode;
  private int page;
  private int size;

}
