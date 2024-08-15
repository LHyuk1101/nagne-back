package com.nagne.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PopularDestinationDto {
  private final String thumbnailUrl;
  private final String title;
  private final String overview;
}