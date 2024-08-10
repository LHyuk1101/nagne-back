package com.nagne.domain.plan.dto;

import java.time.LocalDate;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlanDto {
    private Long id;
    private Long userId;
    private String status;
    private LocalDate startDay;
    private LocalDate endDay;
    private Integer areaCode;
    private String label;
    private int duration;
    private List<PlaceDetail> places; // New field to store place details


    public PlanDto(Long id, Long userId, String status, LocalDate startDay, LocalDate endDay,
                   List<PlaceDetail> places) {
        this.id = id;
        this.userId = userId;
        this.status = status;
        this.startDay = startDay;
        this.endDay = endDay;
        this.duration = calculateDuration();
        this.places = places;
    }

    private int calculateDuration() {
        return endDay != null && startDay != null ? (int) java.time.temporal.ChronoUnit.DAYS.between(startDay, endDay)
                + 1 : 0;
    }

    @Getter
    @Setter
    public static class PlaceDetail {
        private String title;
        private String contentTypeName;

        public PlaceDetail(String title, String contentTypeName) {
            this.title = title;
            this.contentTypeName = contentTypeName;
        }
    }
}