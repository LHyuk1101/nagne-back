package com.nagne.domain.place.entity;

import com.nagne.domain.plan.entity.Plan;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

/**
 *  연관관계 주인이 되는 엔티티 .
 */

@Entity
@Getter
@Builder
@Table(name = "area")
@DynamicInsert
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Area {

    @Id
    private Integer areaCode;

    @Column(length = 100)
    private String name;

    @Builder.Default
    @OneToMany(mappedBy = "area")
    private List<Place> places = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "areaCode")
    private List<Plan> plans = new ArrayList<>();
}
