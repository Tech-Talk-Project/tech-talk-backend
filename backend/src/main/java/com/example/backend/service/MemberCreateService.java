package com.example.backend.service;

import com.example.backend.chat.domain.ChatMember;
import com.example.backend.chat.repository.ChatMemberRepository;
import com.example.backend.entity.member.Authority;
import com.example.backend.entity.member.EAuthority;
import com.example.backend.entity.member.Member;
import com.example.backend.entity.member.MemberAuthority;
import com.example.backend.entity.profile.Profile;
import com.example.backend.oauth2.OAuth2Provider;
import com.example.backend.oauth2.dto.UserProfileDto;
import com.example.backend.repository.member.AuthorityRepository;
import com.example.backend.repository.member.MemberAuthorityRepository;
import com.example.backend.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberCreateService {
    private final MemberRepository memberRepository;
    private final AuthorityRepository authorityRepository;
    private final MemberAuthorityRepository memberAuthorityRepository;
    private final ChatMemberRepository chatMemberRepository;

    public Member createUser(UserProfileDto userProfileDto, OAuth2Provider oAuth2Provider) {
        String email = userProfileDto.getEmail();
        String name = userProfileDto.getName();
        String imageUrl = userProfileDto.getImageUrl();
        Member member = Member.builder()
                .email(email)
                .name(name)
                .imageUrl(imageUrl)
                .oAuth2Provider(oAuth2Provider)
                .build();
        memberRepository.save(member);

        Authority authority = authorityRepository.findByEAuthority(EAuthority.ROLE_USER)
                .orElseThrow(() -> new IllegalArgumentException("ROLE_USER 권한이 존재하지 않습니다."));
        memberAuthorityRepository.save(new MemberAuthority(member, authority));

        chatMemberRepository.save(new ChatMember(member.getId()));
        return member;
    }
}
