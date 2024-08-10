package com.nagne.domain.plan.dto;

import com.nagne.domain.plan.entity.Plan;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
public class PlanResponseDto {
    private Long id;
    private Long userId;
    private String status;
    private LocalDate startDay;
    private LocalDate endDay;
    private Integer areaCode;
    private String subject;
    private Plan.PlanType type;
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
        private Long placeId;
        private String title;
        private String contentType;
        private int order;
        private int moveTime;
        private String placeSummary;
        private String reasoning;
        private String thumbnailUrl;
    }
}