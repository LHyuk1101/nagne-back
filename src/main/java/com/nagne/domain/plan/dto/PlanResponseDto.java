package com.nagne.domain.plan.dto;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class PlanResponseDto {
    private String subject;
    private List<PlanDay> planDays;

    @Getter
    @NoArgsConstructor
    public static class PlanDay {
        private int day;
        private List<Place> places;
    }

    @Getter
    @NoArgsConstructor
    public static class Place {
        private int order;
        private String title;
        private int moveTime;
        private String placeSummary;
        private String reasoning;
    }
}