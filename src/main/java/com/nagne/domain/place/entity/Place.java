package com.nagne.domain.place.entity;

import com.nagne.domain.plan.entity.Plan;
import com.nagne.domain.plan.entity.PlanPlace;
import com.nagne.global.util.BaseEntity;
import jakarta.persistence.*;
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
@NoArgsConstructor
@AllArgsConstructor
public class Place extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "area_code")
    private Area area;

    @Column(length = 200)
    private String title;

    @Column(length = 100)
    private String address;

    @Column(name = "content_id")
    private Integer contentId;

    @Column(name = "content_type_id")
    private Long contentTypeId;

    @Column(columnDefinition = "LONGTEXT")
    private String content;

    private Double lat;

    private Double lng;

    private Integer likes;

    @Column(name = "modified_date")
    private LocalDateTime modifiedDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "api_type")
    private ApiType apiType;

    @OneToMany(mappedBy = "place", cascade = CascadeType.ALL)
    private List<PlaceImg> placeImgs = new ArrayList<>();

    @OneToOne(mappedBy = "place", cascade = CascadeType.ALL)
    private Store store;

    @OneToMany(mappedBy = "place", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PlanPlace> planPlaces = new ArrayList<>();


    public enum ApiType {
        TOUR, GOOGLE, NONE
    }
}