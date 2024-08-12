package com.nagne.domain.plan.dto;

import java.time.LocalDate;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PlanRequestDto {

  private String duration; // "X days" 형식
  private List<PlaceInfo> places;
  private List<PlaceDistance> placeDistances;

  // New fields to support existing logic
  private LocalDate startDay;
  private LocalDate endDay;
  private Integer areaCode;

  @Getter
  @Builder
  public static class PlaceInfo {

    private Long id;
    private String name;
    private String type; // ContentTypeId 값
  }

  @Getter
  @Builder
  public static class PlaceDistance {

    private Long fromPlaceId;
    private Long toPlaceId;
    private double distance;
  }
}
