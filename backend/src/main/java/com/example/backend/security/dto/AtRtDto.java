package com.example.backend.security.dto;

import lombok.Data;

@Data
public class AtRtDto {
    private String accessToken;
    private String refreshToken;
    private Long accessTokenExpirationInMilliseconds;
    private Long refreshTokenExpirationInMilliseconds;

    public AtRtDto(String accessToken, String refreshToken,
                   Long accessTokenExpirationInMilliseconds,
                   Long refreshTokenExpirationInMilliseconds) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.accessTokenExpirationInMilliseconds = accessTokenExpirationInMilliseconds;
        this.refreshTokenExpirationInMilliseconds = refreshTokenExpirationInMilliseconds;
    }
}
