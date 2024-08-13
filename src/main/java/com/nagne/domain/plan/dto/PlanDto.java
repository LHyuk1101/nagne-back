package com.nagne.domain.plan.dto;

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
  private String label;
  private int duration;
  private String thumbnailUrl;
  private List<PlaceDetail> places; // New field to store place details


  public PlanDto(Long id, Long userId, String status, LocalDate startDay, LocalDate endDay,String thumbnailUrl, List<PlaceDetail> places) {
    this.id = id;
    this.userId = userId;
    this.status = status;
    this.startDay = startDay;
    this.endDay = endDay;
    this.duration = calculateDuration();
    this.thumbnailUrl = thumbnailUrl;
    this.places = places;
  }

  public PlanDto(Long id, Long userId, Status status, LocalDate startDay, LocalDate endDay, String thumbnailUrl, String areaCodeName, String label) {
    this.id = id;
    this.userId = userId;
    this.status = status.getName();
    this.startDay = startDay;
    this.endDay = endDay;
    this.duration = calculateDuration();
    this.thumbnailUrl = thumbnailUrl;
    this.areaCodeName = areaCodeName;
    this.label = label;
  }
  
  private int calculateDuration() {
    return endDay != null && startDay != null ?
      (int) java.time.temporal.ChronoUnit.DAYS.between(startDay, endDay)
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