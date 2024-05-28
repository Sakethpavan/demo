package com.example.demo.interceptor;

import com.example.demo.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collections;

@Component
public class AuthInterceptor implements ClientHttpRequestInterceptor {

    @Autowired
    private AuthService authService;

    @Override
    @NonNull
    public ClientHttpResponse intercept(@NonNull HttpRequest request, @NonNull byte[] body, @NonNull ClientHttpRequestExecution execution) throws IOException {
        HttpHeaders headers = request.getHeaders();

        // Add Authorization header if not present
        if (!headers.containsKey(HttpHeaders.AUTHORIZATION)) {
            String accessToken = authService.getAccessToken();
            headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
        }

        // Set Accept header to application/json only
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.valueOf("application/vnd.akeneo.collection+json"));
        return execution.execute(request, body);
    }
}
