package com.nagne.domain.plan.dto;

import com.nagne.domain.place.dto.PlaceDTO;
import com.nagne.domain.plan.entity.Plan.Status;
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
  private String areaCodeName;
  private String subject;
  private String thumbnail;
  private int duration;
  private List<PlaceDTO> placeDTOs;

  public PlanDto(Long id, Long userId, Status status, LocalDate startDay, LocalDate endDay, String thumbnail, String areaCodeName, String subject) {
    this.id = id;
    this.userId = userId;
    this.status = status.getName();
    this.startDay = startDay;
    this.endDay = endDay;
    this.duration = calculateDuration();
    this.thumbnail = thumbnail;
    this.areaCodeName = areaCodeName;
    this.subject = subject;
  }

  public PlanDto(Long id, Long userId, Status status, LocalDate startDay, LocalDate endDay, String subject, String thumbnail) {
    this.id = id;
    this.userId = userId;
    this.status = status.getName();
    this.startDay = startDay;
    this.endDay = endDay;
    this.subject = subject;
    this.duration = calculateDuration();
    this.thumbnail = thumbnail;
  }

  private int calculateDuration() {
    return endDay != null && startDay != null ?
      (int) java.time.temporal.ChronoUnit.DAYS.between(startDay, endDay)
        + 1 : 0;
  }
}