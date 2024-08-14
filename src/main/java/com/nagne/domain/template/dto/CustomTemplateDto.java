package com.nagne.domain.template.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CustomTemplateDto {

  private Long id;
  private String subject;
  private String overview;
  private String thumbnailUrl;
  private int areaCode;
}