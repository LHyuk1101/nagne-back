package com.nagne.domain.place.dto;

import com.nagne.domain.place.entity.Area;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PlaceDTO {

  private Long id;
  private Area area;
  private Integer areaCode;
  private String title;
  private String address;
  private Long contentTypeId;
  private String overview;
  private String infocenter;
  private Double lat;
  private Double lng;
  private int likes;
  private String thumbnailUrl;
  private List<String> placeUrlImages;

}
