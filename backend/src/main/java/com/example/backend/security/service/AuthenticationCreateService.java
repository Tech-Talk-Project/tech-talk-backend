package com.example.backend.security.service;

import com.example.backend.repository.member.MemberAuthorityRepository;
import com.example.backend.security.jwt.JwtClaimReader;
import com.example.backend.security.jwt.JwtValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class AuthenticationCreateService {
    private final MemberAuthorityRepository memberAuthorityRepository;
    private final JwtValidator jwtValidator;
    private final JwtClaimReader jwtClaimReader;

    private Authentication createAuthentication(String token) {
        Long memberId = jwtClaimReader.getMemberId(token);
        Collection<GrantedAuthority> authorities = memberAuthorityRepository.findAuthoritiesByMemberId(memberId)
                .stream()
                .map(authority -> (GrantedAuthority) authority::getName)
                .toList();

        return new UsernamePasswordAuthenticationToken(memberId, token, authorities);
    }

    public Authentication createAuthenticationByAt(String accessToken) {
        jwtValidator.validateAccessToken(accessToken);
        return createAuthentication(accessToken);
    }

    public Authentication createAuthenticationByRt(String refreshToken) {
        jwtValidator.validateRefreshToken(refreshToken);
        return createAuthentication(refreshToken);
    }
}
