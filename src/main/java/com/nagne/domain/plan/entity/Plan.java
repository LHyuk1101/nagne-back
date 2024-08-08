package com.nagne.domain.plan.entity;

import com.nagne.domain.place.entity.Area;
import com.nagne.domain.review.entity.Review;
import com.nagne.domain.user.entity.User;
import com.nagne.global.util.BaseEntity;
import jakarta.persistence.*;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
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
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Plan extends BaseEntity {

    @Id
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

    private LocalDate startDay;

    private LocalDate endDay;

    private String subject;

    @Column(columnDefinition = "LONGTEXT")
    private String overView;

    private String placeSummary;

    private String reasoning;

    private Time totalMoveTime;

    @Builder.Default
    @OneToMany(mappedBy = "plan")
    private List<Review> reviews = new ArrayList<>();

    public enum Status {
        BEGIN, END
    };
}