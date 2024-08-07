package com.nagne.domain.plan.dto;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

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

    public PlanDto(Long id, Long userId, String status, LocalDate startDay, LocalDate endDay, Integer areaCode, String label) {
        this.id = id;
        this.userId = userId;
        this.status = status;
        this.startDay = startDay;
        this.endDay = endDay;
        this.areaCode = areaCode;
        this.label = label;
        this.duration = calculateDuration();
    }

    private int calculateDuration() {
        return endDay != null && startDay != null ? (int) java.time.temporal.ChronoUnit.DAYS.between(startDay, endDay) + 1 : 0;
    }
}