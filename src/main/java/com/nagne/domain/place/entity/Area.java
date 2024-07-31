package com.nagne.domain.place.entity;

import com.nagne.domain.plan.entity.Plan;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@Table(name = "area")
@NoArgsConstructor
@AllArgsConstructor
public class Area {

    @Id
    @Column(name = "area_code")
    private Integer areaCode;

    @Column(length = 100)
    private String name;

    @OneToMany(mappedBy = "area", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Place> places = new ArrayList<>();

    @OneToMany(mappedBy = "areaCode", fetch = FetchType.LAZY)
    private List<Plan> plans = new ArrayList<>();
}
