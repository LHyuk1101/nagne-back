package com.nagne.domain.place.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PopularDestinationDto {
  
  private Long id;
  private String address;
  private String contactNumber;
  private String imgUrl;
  private Integer likes;
  private final String thumbnailUrl;
  private final String title;
  private final String overview;
}