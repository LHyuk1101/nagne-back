package com.nagne.domain.plan.entity;

import com.nagne.domain.place.entity.Area;
import com.nagne.domain.user.entity.User;
import com.nagne.global.util.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Getter
@Builder
@Table(name = "plans")
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Plan extends BaseEntity {
  
  @Id
  @Column(name = "plans_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;
  
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "area_code")
  private Area area;
  
  @Enumerated(EnumType.STRING)
  private Status status;
  
  @Enumerated(EnumType.ORDINAL)
  @Column(columnDefinition = "TINYINT")
  private PlanType type;
  
  private LocalDate startDay;
  
  private LocalDate endDay;
  
  private String subject;
  
  @Column(columnDefinition = "LONGTEXT")
  private String overview;
  
  @Column(length = 500)
  private String thumbnail;

//  @Builder.Default
//  @OneToMany(mappedBy = "plan")
//  private List<Review> reviews = new ArrayList<>();
  
  public enum Status {
    BEGIN, END
  }
  
  public enum PlanType {
    LLM, CUSTOM
  }
}