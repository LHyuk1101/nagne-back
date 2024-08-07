package com.nagne.domain.place.entity;

import com.nagne.domain.plan.entity.Plan;
import com.nagne.domain.plan.entity.PlanPlace;
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
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "area_code")
    private Area area;

    @Column(length = 200)
    private String title; // 장소명

    @Column(length = 100)
    private String address;

    private Integer contentId;

    private Long contentTypeId; // 숙소(81), 맛집, 관광지

//    @Column(columnDefinition = "LONGTEXT")
//    private String content;

    private Double lat;

    private Double lng;

    private Integer likes;

    private LocalDateTime modifiedDate;

    @Enumerated(EnumType.STRING)
    private ApiType apiType;

    @Builder.Default
    @OneToMany(mappedBy = "place", cascade = CascadeType.ALL)
    private List<PlaceImg> placeImgs = new ArrayList<>();

    @OneToOne(mappedBy = "place")
    private Store store;


    public enum ApiType {
        TOUR, GOOGLE, NONE
    }
}