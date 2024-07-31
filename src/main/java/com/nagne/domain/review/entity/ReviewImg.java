package com.nagne.domain.review.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@Table(name = "review_img")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ReviewImg {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    private Review review;

    @Column(name = "byte_size")
    private Integer byteSize;

    private Integer height;

    private Integer width;

    @Column(length = 100)
    private String domain;

    @Column(length = 500)
    private String domainPath;

    @Column(name = "thumbnail_yn")
    private Boolean thumbnailYn;
}
