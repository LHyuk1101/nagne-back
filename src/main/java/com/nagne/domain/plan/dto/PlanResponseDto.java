package com.nagne.domain.plan.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlanResponseDto {
    private String subject;
    private String placeSummary;
    private String reasoning;
    private List<PlanDay> planDayList;


    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class PlanDay {
        private int day;
        private List<PlanPlace> planPlaceList;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class PlanPlace {
        private int order;
        private String title;
        private int moveTime;
    }
}