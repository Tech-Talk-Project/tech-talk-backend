package com.example.backend.entity.member;

import com.example.backend.entity.BaseEntity;
import com.example.backend.entity.profile.Profile;
import com.example.backend.oauth2.OAuth2Provider;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private long id;

    private String name;

    @Column(unique = true)
    private String email;

    private String imageUrl;

    @Enumerated(EnumType.STRING)
    private OAuth2Provider oAuth2Provider;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "profile_id")
    private Profile profile = new Profile();

    @Builder
    public Member(String name, String email, String imageUrl, OAuth2Provider oAuth2Provider) {
        this.name = name;
        this.email = email;
        this.imageUrl = imageUrl;
        this.oAuth2Provider = oAuth2Provider;
    }
}
