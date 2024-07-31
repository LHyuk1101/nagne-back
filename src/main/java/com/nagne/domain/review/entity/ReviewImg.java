package com.nagne.domain.review.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@Table(name = "review_img")
@NoArgsConstructor
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

    @Column(length = 255)
    private String url;

    @Column(length = 255)
    private String type;

    @Column(name = "thumbnail_yn")
    private Boolean thumbnailYn;
}
