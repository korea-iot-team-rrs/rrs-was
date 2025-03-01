package com.korit.projectrrs.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "community_likes", uniqueConstraints = {
        @UniqueConstraint(name = "unique_like", columnNames = {"community_id", "user_id"})
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommunityLikes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LIKE_ID", nullable = false, updatable = false)
    private Long likeId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "COMMUNITY_ID", nullable = false)
    private Community community;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @Column(name = "USER_LIKED", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean userLiked;

    public Long getUserId() {
        return user != null ? user.getUserId() : null;
    }

    public String getNickname() {
        return user != null ? user.getNickname() : null;
    }
}

