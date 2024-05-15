package com.example.demo.service;

import com.example.demo.config.OAuthProperties;
import com.example.demo.dto.AuthenticationResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class AuthService {
    private final RestTemplate restTemplate = new RestTemplate();

    private final OAuthProperties oAuthProperties;
    private String accessToken;
    private String refreshToken;
    private Long expiresIn;

    @Autowired
    public AuthService(OAuthProperties oAuthProperties) {
        this.oAuthProperties = oAuthProperties;
    }

    public String getAccessToken() {
        // Check if the access token has expired
        if (this.accessToken == null) {
            updateAccessAndRefreshTokens();
        } else {
            if (System.currentTimeMillis() > this.expiresIn) {
                // Refresh the access token
                updateAccessTokenFromRefreshToken();
            }
        }

        return this.accessToken;
    }


    public void updateAccessAndRefreshTokens() {
        String username = oAuthProperties.getUsername();
        String password = oAuthProperties.getPassword();
        String authString = Base64.getEncoder().encodeToString(
                (oAuthProperties.getClientId() + ":" + oAuthProperties.getSecret()).getBytes()
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add(HttpHeaders.AUTHORIZATION, "Basic " + authString);

        HttpEntity<String> entity = new HttpEntity<>(
                "{\"grant_type\":\"password\",\"username\":\"" + username + "\",\"password\":\"" + password + "\"}", headers);

        ResponseEntity<AuthenticationResponseDTO> response = restTemplate.postForEntity(oAuthProperties.getTokenEndpoint(), entity, AuthenticationResponseDTO.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            AuthenticationResponseDTO responseBody = Objects.requireNonNull(response.getBody());
            this.accessToken = responseBody.getAccess_token();
            this.refreshToken = responseBody.getRefresh_token();
            this.expiresIn = (responseBody.getExpires_in() * 1000L) + System.currentTimeMillis();
        } else {
            throw new RuntimeException("Failed to obtain token");
        }
    }

    private void updateAccessTokenFromRefreshToken() {
        // Prepare the request body for refreshing the access token
        String requestBody = "{\"refresh_token\":\"" + this.refreshToken + "\",\"grant_type\":\"refresh_token\"}";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<AuthenticationResponseDTO> response = restTemplate.postForEntity(oAuthProperties.getTokenEndpoint(), entity, AuthenticationResponseDTO.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            AuthenticationResponseDTO responseBody = Objects.requireNonNull(response.getBody());
            this.accessToken = responseBody.getAccess_token();
            this.refreshToken = responseBody.getRefresh_token();
            this.expiresIn = (responseBody.getExpires_in() * 1000L) + System.currentTimeMillis();
        } else {
            throw new RuntimeException("Failed to refresh access token");
        }
    }

//    public String getToken() {
//        String username = oAuthProperties.getUsername();
//        String password = oAuthProperties.getPassword();
//        String authString = Base64.getEncoder().encodeToString(
//                (oAuthProperties.getClientId() + ":" + oAuthProperties.getSecret()).getBytes()
//        );
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.add(HttpHeaders.AUTHORIZATION, "Basic " + authString);
//
//        HttpEntity<String> entity = new HttpEntity<>(
//                "{\"grant_type\":\"password\",\"username\":\"" + username + "\",\"password\":\"" + password + "\"}", headers);
//
//        ResponseEntity<AuthenticationResponseDTO> response = restTemplate.postForEntity(oAuthProperties.getTokenEndpoint(), entity, AuthenticationResponseDTO.class);
//        if (response.getStatusCode().is2xxSuccessful()) {
//            return Objects.requireNonNull(response.getBody()).getAccess_token();
//        } else {
//            throw new RuntimeException("Failed to obtain access token");
//        }
//    }
}
