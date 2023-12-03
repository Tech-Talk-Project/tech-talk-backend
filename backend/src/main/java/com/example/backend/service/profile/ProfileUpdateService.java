package com.example.backend.service.profile;

import com.example.backend.controller.dto.request.UpdateDescRequestDto;
import com.example.backend.controller.dto.request.UpdateInfoRequestDto;
import com.example.backend.controller.dto.request.UpdateIntroductionRequestDto;
import com.example.backend.controller.dto.request.UpdateLinksRequestDto;
import com.example.backend.entity.profile.Profile;
import com.example.backend.repository.profile.MemberProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ProfileUpdateService {
    private final MemberProfileRepository memberProfileRepository;

    public void updateInfo(Long memberId, UpdateInfoRequestDto updateInfoRequestDto) {
        String nickname = updateInfoRequestDto.getName();
        String job =  updateInfoRequestDto.getJob();
        memberProfileRepository.updateProfileInfo(memberId, nickname, job);
    }

    public void updateIntroduction(Long memberId, UpdateIntroductionRequestDto updateIntroductionRequestDto) {
        String introduction = updateIntroductionRequestDto.getIntroduction();
        memberProfileRepository.updateProfileIntroduction(memberId, introduction);
    }

    public void updateDescription(Long memberId, UpdateDescRequestDto updateDescRequestDto) {
        String detailedDescription = updateDescRequestDto.getDetailedDescription();
        memberProfileRepository.updateProfileDescription(memberId, detailedDescription);
    }

    public void updateLinks(Long memberId, UpdateLinksRequestDto updateLinksRequestDto) {
        Profile profileWithLinks = memberProfileRepository.findProfileWithLinks(memberId);
        profileWithLinks.updateLinks(updateLinksRequestDto.getLinks());
    }
}
