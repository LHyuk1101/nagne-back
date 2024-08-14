package com.nagne.domain.template.entity;

import com.nagne.domain.place.entity.Place;
import com.nagne.domain.plan.entity.Plan;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@Table(name = "templates")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Template {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  @Column(name = "days")
  private Integer day;
  
  @Column(name = "orders")
  private Integer order;
  
  private Integer moveTime;
  
  @Column(columnDefinition = "LONGTEXT")
  private String placeSummary;
  
  @Column(columnDefinition = "LONGTEXT")
  private String reasoning;
  
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "plan_id")
  private Plan plan;
  
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "place_id")
  private Place place;
}
