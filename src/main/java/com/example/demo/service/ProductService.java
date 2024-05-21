package com.example.demo.service;

import com.example.demo.config.APIEndpoints;
import com.example.demo.dto.ProductDTO;
import com.example.demo.exception.CustomException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ProductService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AuthService authService;

    @Autowired
    private APIEndpoints apiEndpoints;

    Logger logger = LogManager.getLogger(ProductService.class);

    public String getProducts() {
        ResponseEntity<String> response = restTemplate.exchange(
                apiEndpoints.getProductsEndpoint(),
                HttpMethod.GET,
                null,
                String.class
        );
        logger.info("products: {}", response);
        return response.getBody();
    }

    public ProductDTO getProduct(String code) {
        String getProductEndpoint = apiEndpoints.getProductsEndpoint() + "/" + code;
        ResponseEntity<ProductDTO> response = restTemplate.exchange(
                getProductEndpoint,
                HttpMethod.GET,
                null,
                ProductDTO.class
        );
        logger.info("product: {}", response.getBody());
        return response.getBody();
    }

    public void updateProduct(String code, ProductDTO partialUpdateBody) {
        HttpEntity<ProductDTO> entity = new HttpEntity<>(partialUpdateBody);
        String updateProductEndpoint = apiEndpoints.getProductsEndpoint() + "/" + code;
        restTemplate.exchange(updateProductEndpoint, HttpMethod.PATCH, entity, Void.class);
        logger.info("Successfully updated product: {}", code);
    }

    public void createProduct(ProductDTO newProduct) {
        HttpEntity<ProductDTO> entity = new HttpEntity<>(newProduct);
        String createProductEndpoint = apiEndpoints.getProductsEndpoint();
        restTemplate.exchange(createProductEndpoint, HttpMethod.POST, entity, ProductDTO.class);
        logger.info("created product: {}", newProduct.getIdentifier());
    }
}
