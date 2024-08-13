package com.nagne.domain.plan.dto;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter

public class PlanRequestDto {

  private final List<PlaceInfo> places;
  private final LocalDate startDay;
  private final LocalDate endDay;
  private final Integer areaCode;


  @Builder
  public PlanRequestDto(List<PlaceInfo> places, List<PlaceDistance> placeDistances,
    LocalDate startDay, LocalDate endDay, Integer areaCode) {
    this.places = places;
    this.startDay = startDay;
    this.endDay = endDay;
    this.areaCode = areaCode;
  }

  public int getDuration() {
    return (int) ChronoUnit.DAYS.between(startDay, endDay) + 1;
  }

  @Getter
  @Builder
  public static class PlaceInfo {

    private final List<PlaceDistance> placeDistances;
    private Long id;
    private String name;
    private Long contentType;
    private String overview;

  }


  @Getter
  @Builder
  public static class PlaceDistance {

    private Long fromPlaceId;
    private Long toPlaceId;
    private double distance;
  }
}
