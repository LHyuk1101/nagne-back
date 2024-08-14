package com.nagne.domain.template.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CustomTemplateDto {

  private Long id;
  private String subject;
  private String overview;
  private String thumbnailUrl;
  private String areaCode;
}