package com.nagne.domain.place.entity;

import com.nagne.global.util.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

  @Builder.Default
  @OneToMany(mappedBy = "place", cascade = CascadeType.ALL)
  private List<PlaceImg> placeImgs = new ArrayList<>();

  public enum ApiType {
    TOUR, GOOGLE, NONE
  }
}