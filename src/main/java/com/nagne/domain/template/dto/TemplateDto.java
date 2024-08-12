package com.nagne.domain.template.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TemplateDto {
  private String title;
  private String description;
  private String thumbnailUrl;
}