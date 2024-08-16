package com.nagne.domain.place.entity;

import com.nagne.global.util.BaseEntity;
import jakarta.persistence.CascadeType;
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
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@Table(name = "place")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Place extends BaseEntity {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "place_id")
  private Long id;
  
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "area_code")
  private Area area;
  
  @Column(length = 200)
  private String title;
  
  @Column(length = 100)
  private String address;
  
  private Integer contentId;
  
  private Long contentTypeId;
  
  @Column(columnDefinition = "LONGTEXT")
  private String overview;
  
  private Double lat;
  
  private Double lng;
  
  private Integer likes;
  
  private LocalDateTime modifiedTime;
  
  @Enumerated(EnumType.STRING)
  private ApiType apiType;
  
  @Column(length = 500)
  private String thumbnailUrl;
  @Builder.Default
  @OneToMany(mappedBy = "place", cascade = CascadeType.ALL)
  private List<PlaceImg> placeImgs = new ArrayList<>();
  
  @OneToOne(mappedBy = "place", fetch = FetchType.LAZY)
  private Store store;
  
  public enum ApiType {
    TOUR, GOOGLE, NONE
  }
  
  
}