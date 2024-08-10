package com.nagne.domain.plan.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class PlanRequestDto {
    private String duration; // "X days" 형식
    private List<PlaceInfo> places;
    private List<PlaceDistance> placeDistances;

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