package com.nagne.domain.plan.dto;

import java.time.LocalDate;
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

    public PlanDto(Long id, Long userId, String status, LocalDate startDay, LocalDate endDay) {
        this.id = id;
        this.userId = userId;
        this.status = status;
        this.startDay = startDay;
        this.endDay = endDay;
        this.duration = calculateDuration();
    }

    private int calculateDuration() {
        return endDay != null && startDay != null ? (int) java.time.temporal.ChronoUnit.DAYS.between(startDay, endDay)
                + 1 : 0;
    }
}