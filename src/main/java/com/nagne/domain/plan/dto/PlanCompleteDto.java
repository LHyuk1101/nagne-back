package com.nagne.domain.plan.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
public class PlanCompleteDto {
    private Long id;
    private Long userId;
    private String status;
    private LocalDate startDay;
    private LocalDate endDay;
    private Integer areaCode;
    private String subject;
    private List<DayPlan> dayPlans;

    @Getter
    @Builder
    public static class DayPlan {
        private int day;
        private List<PlaceDetail> places;
    }

    @Getter
    @Builder
    public static class PlaceDetail {
        private Long id;
        private String title;
        private String contentType;
        private int order;
        private int moveTime;
        private String placeSummary;
        private String reasoning;
        private String thumbnailUrl;
    }
}