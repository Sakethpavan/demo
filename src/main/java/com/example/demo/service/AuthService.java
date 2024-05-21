package com.example.demo.service;

import com.example.demo.config.AuthProperties;
import com.example.demo.dto.AuthenticationResponseDTO;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;
import java.util.Objects;

@Service
public class AuthService {
    private final RestTemplate restTemplate = new RestTemplate();

    private String accessToken;
    private String refreshToken;
    private Long expiresIn;

    private final AuthProperties authProperties;
    public AuthService(AuthProperties authProperties) {
        this.authProperties = authProperties;
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

    private void updateAccessAndRefreshTokens() {
        String username = authProperties.getUsername();
        String password = authProperties.getPassword();
        String authString = Base64.getEncoder().encodeToString(
                (authProperties.getClientId() + ":" + authProperties.getSecret()).getBytes()
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add(HttpHeaders.AUTHORIZATION, "Basic " + authString);

        HttpEntity<String> entity = new HttpEntity<>(
                "{\"grant_type\":\"password\",\"username\":\"" + username + "\",\"password\":\"" + password + "\"}", headers);

        ResponseEntity<AuthenticationResponseDTO> response = restTemplate.postForEntity(authProperties.getTokenEndpoint(), entity, AuthenticationResponseDTO.class);

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

        ResponseEntity<AuthenticationResponseDTO> response = restTemplate.postForEntity(authProperties.getTokenEndpoint(), entity, AuthenticationResponseDTO.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            AuthenticationResponseDTO responseBody = Objects.requireNonNull(response.getBody());
            this.accessToken = responseBody.getAccess_token();
            this.refreshToken = responseBody.getRefresh_token();
            this.expiresIn = (responseBody.getExpires_in() * 1000L) + System.currentTimeMillis();
        } else {
            throw new RuntimeException("Failed to refresh access token");
        }
    }
}
