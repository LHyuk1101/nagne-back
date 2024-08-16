package com.nagne.domain.template.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Builder
@Getter
@RequiredArgsConstructor
public class TemplateDto {

  private Long id;
  private int days;
  private int order;
  private int moveTime;
  private Long placeId;
  private String title;
  private Long contentType;
  private String placeSummary;
  private String reasoning;
  private String placeImgUrls;

  public TemplateDto(Long id, Integer days, Integer order, Integer moveTime, Long placeId,
    String title,
    Long contentTypeId, String placeSummary, String reasoning, String placeImgUrls) {
    this.id = id;
    this.days = days;
    this.order = order;
    this.moveTime = moveTime;
    this.placeId = placeId;
    this.title = title;
    this.contentType = contentTypeId;
    this.placeSummary = placeSummary;
    this.reasoning = reasoning;
    this.placeImgUrls = placeImgUrls;
  }
}
