package com.example.demo.config;

import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.demo.interceptor.AuthInterceptor;

import java.util.List;

@Configuration
public class RestClientConfig {

    @Autowired
    private AuthInterceptor authInterceptor;

    @Bean
    public RestTemplate restTemplate() {
        HttpClient httpClient = HttpClientBuilder.create().build();
        RestTemplate restTemplate = new RestTemplate();
        // add HTTP PATCH support to restTemplate
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(httpClient));

        // register interceptor
        List<ClientHttpRequestInterceptor> interceptors = restTemplate.getInterceptors();
        interceptors.add(authInterceptor);
        restTemplate.setInterceptors(interceptors);

        return restTemplate;
    }
}