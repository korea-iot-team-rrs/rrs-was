package com.korit.projectrrs.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "USERS")
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID", nullable = false)
    private Long userId;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "USERNAME", nullable = false, unique = true)
    private String username;

    @JsonIgnoreProperties
    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @Column(name = "NICKNAME", nullable = false, unique = true)
    private String nickname;

    @Column(name = "PHONE", nullable = false, unique = true)
    private String phone;

    @Column(name = "ADDRESS", nullable = false)
    private String address;

    @Column(name = "ADDRESS_DETAIL", nullable = false)
    private String addressDetail;

    @Column(name = "EMAIL", nullable = false, unique = true)
    private String email;

    @Column(name = "PROFILE_IMAGE_URL")
    private String profileImageUrl;

    @Column(name = "ROLES", nullable = false)
    private String roles;

    @Column(name = "PROVIDER_INTRODUCTION", columnDefinition = "TEXT")
    private String providerIntroduction;

    @Column(nullable = false, length = 5, columnDefinition = "VARCHAR(5) COMMENT '가입 경로 (HOME, KAKAO, NAVER)'")
    private String joinPath;

    @Column(nullable = true, columnDefinition = "VARCHAR(255) COMMENT 'OAuth2 사용자 아이디'")
    private String snsId;

    @PrePersist
    private void setDefaultValues() {
        if (this.joinPath == null) {
            this.joinPath = "HOME";
        }
    }

    @Builder.Default
    @JsonManagedReference
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pet> pet = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Todo> todos = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Reservation> reservations = new ArrayList<>();

    @OneToMany(mappedBy = "provider", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Reservation> provisions = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<CustomerSupport> customerSupports = new ArrayList<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "provider", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<AvailableDateOfWeek> availableDates;
}