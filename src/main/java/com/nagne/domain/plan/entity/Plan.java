package com.nagne.domain.plan.entity;

import com.nagne.domain.review.entity.Review;
import com.nagne.domain.user.entity.User;
import com.nagne.global.util.BaseEntity;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import java.time.LocalDate;

@Entity
@Getter
@Builder
@Table(name = "plans")
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
public class Plan extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "start_day")
    private LocalDate startDay;

    @Column(name = "end_day")
    private LocalDate endDay;

    @Column(name = "area_code")
    private Integer areaCode;

    @Column(length = 100)
    private String label;

    @OneToMany(mappedBy = "plan", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Template> templates = new ArrayList<>();

    @OneToMany(mappedBy = "plan", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PlanPlace> planPlaces = new ArrayList<>();

    @OneToMany(mappedBy = "plan", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();


    public enum Status {
        BEGIN, END
    };
}