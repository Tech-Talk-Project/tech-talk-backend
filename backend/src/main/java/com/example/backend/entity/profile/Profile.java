package com.example.backend.entity.profile;

import com.example.backend.entity.BaseEntity;
import com.example.backend.entity.member.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
public class Profile extends BaseEntity {
    @Id @GeneratedValue
    @Column(name = "profile_id")
    private Long id;

    @OneToOne(mappedBy = "profile")
    private Member member;

    private String job;

    private String introduction;

    @Lob
    @Column(columnDefinition = "MEDIUMTEXT")
    private String detailedDescription;

    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Link> links = new ArrayList<>();

    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ProfileSkill> profileSkills = new HashSet<>();

    public void updateLinks(List<String> links) {
        this.links.clear();
        for (String link : links) {
            addLink(link);
        }
    }

    private void addLink(String link) {
        this.links.add(new Link(link, this));
    }

    public void updateProfileSkills(List<Skill> skills) {
        this.profileSkills.clear();
        for (Skill skill : skills) {
            addProfileSkill(skill);
        }
    }

    private void addProfileSkill(Skill skill) {
        this.profileSkills.add(new ProfileSkill(skill, this));
    }
}
