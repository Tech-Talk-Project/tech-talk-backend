package com.example.backend.auth.dto.response;

import com.example.backend.auth.dto.LoginSuccessDto;
import com.example.backend.entity.member.Member;
import com.example.backend.security.dto.AtRtDto;
import lombok.Data;

@Data
public class LoginResponseDto {
    private Long memberId;
    private boolean firstLogin;
    private String accessToken;
    private Long refreshTokenExpirationInMilliSeconds;

    public LoginResponseDto(Member member, AtRtDto atRtDto, boolean firstLogin) {
        this.memberId = member.getId();
        this.firstLogin = firstLogin;
        this.accessToken = atRtDto.getAccessToken();
        this.refreshTokenExpirationInMilliSeconds = atRtDto.getRefreshTokenExpirationInMilliseconds();
    }

    public LoginResponseDto(LoginSuccessDto loginSuccessDto) {
        this.memberId = loginSuccessDto.getMember().getId();
        this.firstLogin = loginSuccessDto.isFirstLogin();
        this.accessToken = loginSuccessDto.getAtRtDto().getAccessToken();
        this.refreshTokenExpirationInMilliSeconds = loginSuccessDto.getAtRtDto().getRefreshTokenExpirationInMilliseconds();
    }
}
