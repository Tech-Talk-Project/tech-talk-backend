package com.example.backend.controller.dto.response;

import com.example.backend.controller.dto.InfoDto;
import com.example.backend.entity.member.Member;
import com.example.backend.entity.profile.Link;
import com.example.backend.entity.profile.Skill;
import lombok.Data;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class ProfileResponseDto {

    private InfoDto info;

    private String imageUrl;

    private String introduction;

    private List<String> links;

    private List<String> skills;

    private String detailedDescription;

    public ProfileResponseDto(Member member) {
        this.info = new InfoDto(member.getName(), member.getProfile().getJob(), member.getEmail());
        this.imageUrl = member.getImageUrl();
        this.introduction = member.getProfile().getIntroduction();
        this.links = member.getProfile().getLinks().stream()
                .map(Link::getUrl)
                .collect(Collectors.toList());
        this.skills = member.getProfile().getSkills().stream().map(Skill::getName).toList();
        this.detailedDescription = member.getProfile().getDetailedDescription();
    }
}
