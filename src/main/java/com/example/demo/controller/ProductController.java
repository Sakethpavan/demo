package com.example.demo.controller;

import com.example.demo.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ProductController {

    private final RestTemplate restTemplate = new RestTemplate();

    Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private AuthService authService;

    @GetMapping("/products")
    public ResponseEntity<?> fetchProducts() {
        String accessToken = authService.getAccessToken();

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    "https://pim-0f62dfa2cd.trial.akeneo.cloud/api/rest/v1/products",
                    HttpMethod.GET,
                    entity,
                    String.class);
            logger.info("products: {}", response);
            return ResponseEntity.ok(response.getBody());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error fetching products");
        }
    }
}
