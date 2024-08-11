package com.nagne.domain.place.dto;

import com.nagne.domain.place.entity.Area;
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
