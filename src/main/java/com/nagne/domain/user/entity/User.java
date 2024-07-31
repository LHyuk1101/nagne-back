package com.nagne.domain.user.entity;

import com.nagne.global.util.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Getter
@Builder
@Table(name = "users")
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String nickname;

    private Integer nation;
    private String profileImg;
    private UserRole role;
    private Boolean termAgreed;

}

//package com.nagne.domain.user.entity;
//
//import com.nagne.domain.plan.entity.Plan;
//import com.nagne.domain.review.entity.Review;
//import com.nagne.global.util.BaseEntity;
//import jakarta.persistence.CascadeType;
//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.persistence.OneToMany;
//import jakarta.persistence.Table;
//import java.util.ArrayList;
//import java.util.List;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import org.hibernate.annotations.DynamicUpdate;
//
//@Entity
//@Getter
//@Builder
//@Table(name = "users")
//@DynamicUpdate
//@NoArgsConstructor
//@AllArgsConstructor
//public class User extends BaseEntity {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    private String password;
//
//    @Column(nullable = false, unique = true)
//    private String email;
//
//    @Column(name = "nick_name", nullable = false)
//    private String nickName;
//
//    private Integer nation;
//
//    @Column(name = "profile_img")
//    private String profileImg;
//
//    private UserRole role;
//
//    @Column(name = "term_agreed")
//    private Boolean termAgreed;
//
//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Oauthid> oauthIds = new ArrayList<>();
//
//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
//    private List<Plan> plans = new ArrayList<>();
//
//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
//    private List<Review> reviews = new ArrayList<>();
//
//}