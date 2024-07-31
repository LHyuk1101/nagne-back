package com.nagne.domain.review.entity;

import com.nagne.domain.user.entity.User;
import com.nagne.domain.plan.entity.Plan;
import com.nagne.global.util.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@Table(name = "reviews")
@NoArgsConstructor
@AllArgsConstructor
public class Review extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_id")
    private Plan plan;

    @Column(columnDefinition = "LONGTEXT")
    private String text;

    @Column(name = "vote_count")
    private Integer voteCount;

    @Column(name = "contents_id")
    private Long contentsId;

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReviewImg> reviewImgs = new ArrayList<>();
}
