package com.example.backend.oauth2.service;

import com.example.backend.oauth2.OAuth2Provider;
import com.example.backend.oauth2.api.OAuth2AccessTokenFetcher;
import com.example.backend.oauth2.api.OAuth2UserAttributesFetcher;
import com.example.backend.oauth2.dto.OAuth2AccessTokenResponse;
import com.example.backend.oauth2.dto.UserProfileDto;
import com.example.backend.oauth2.util.property.OAuth2Properties;
import com.example.backend.oauth2.util.property.OAuth2Property;
import com.example.backend.oauth2.util.userprofile.UserProfileExtractor;
import com.example.backend.oauth2.util.userprofile.UserProfileExtractorFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class OAuth2UserProfileService {
    private final OAuth2AccessTokenFetcher oAuth2AccessTokenFetcher;
    private final OAuth2UserAttributesFetcher oAuth2UserAttributesFetcher;
    private final OAuth2Properties oAuth2Properties;

    public UserProfileDto getUserProfile(String code, OAuth2Provider provider) {
        OAuth2Property oAuth2Property = oAuth2Properties.get(provider);

        OAuth2AccessTokenResponse accessTokenResponse =
                oAuth2AccessTokenFetcher.fetch(code, oAuth2Property);

        Map<String, Object> attributes = oAuth2UserAttributesFetcher.fetch(
                accessTokenResponse.getAccessToken(), oAuth2Property);

        UserProfileExtractor userProfileExtractor = UserProfileExtractorFactory.get(provider);
        return userProfileExtractor.extract(attributes);
    }
}
