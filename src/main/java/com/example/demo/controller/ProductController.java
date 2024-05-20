package com.example.demo.controller;

import com.example.demo.config.APIEndpoints;
import com.example.demo.dto.ProductDTO;
import com.example.demo.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
public class ProductController {

    @Autowired
    private RestTemplate restTemplate;

    Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private AuthService authService;

    @Autowired
    private APIEndpoints apiEndpoints;

    @GetMapping("/products")
    public ResponseEntity<?> fetchProducts() {
        String accessToken = authService.getAccessToken();

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    apiEndpoints.getProductsEndpoint(),
                    HttpMethod.GET,
                    entity,
                    String.class);
            logger.info("products: {}", response);
            return ResponseEntity.ok(response.getBody());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error fetching products");
        }
    }

    @GetMapping("/products/{code}")
    public ResponseEntity<?> fetchProduct(@PathVariable String code) {
        String accessToken = authService.getAccessToken();

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        String getProductEndpoint = apiEndpoints.getProductsEndpoint() + "/" + code;
        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    getProductEndpoint,
                    HttpMethod.GET,
                    entity,
                    String.class);
            logger.info("product: {}", response);
            return ResponseEntity.ok(response.getBody());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error updating product");
        }
    }

    @PatchMapping("/products/{code}")
    public ResponseEntity<?> updateProduct(@PathVariable String code, @RequestBody ProductDTO partialUpdateBody) {
        String accessToken = authService.getAccessToken();

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);

        HttpEntity<ProductDTO> entity = new HttpEntity<>(partialUpdateBody, headers);
        String updateProductEndpoint = apiEndpoints.getProductsEndpoint() + "/" + code;
        try {
            ResponseEntity<Void> response = restTemplate.exchange(
                    updateProductEndpoint,
                    HttpMethod.PATCH,
                    entity,
                    Void.class);
            logger.info("successfully updated product: {}", code);
            return ResponseEntity.ok(response.getBody());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error while updating product");
        }
    }

    @PostMapping("/products")
    public ResponseEntity<?> createProduct(@RequestBody ProductDTO body) {
        String accessToken = authService.getAccessToken();

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);

        HttpEntity<ProductDTO> entity = new HttpEntity<>(body, headers);
        String createProductEndpoint = apiEndpoints.getProductsEndpoint();
        try {
            ResponseEntity<ProductDTO> response = restTemplate.exchange(
                    createProductEndpoint,
                    HttpMethod.POST,
                    entity,
                    ProductDTO.class);
            logger.info("created product: {}", response);
            return ResponseEntity.ok(response.getBody());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error while creating product");
        }
    }


}
