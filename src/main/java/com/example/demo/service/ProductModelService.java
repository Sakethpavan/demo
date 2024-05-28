package com.example.demo.service;

import com.example.demo.config.APIEndpoints;
import com.example.demo.dto.product.ProductModelDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ProductModelService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AuthService authService;

    @Autowired
    private APIEndpoints apiEndpoints;

    Logger logger = LogManager.getLogger(ProductModelService.class);

    public String getProductModels() {
        ResponseEntity<String> response = restTemplate.exchange(
                apiEndpoints.getProductModelsEndpoint(),
                HttpMethod.GET,
                null,
                String.class
        );
        logger.info("product Models: {}", response);
        return response.getBody();
    }

    public ProductModelDTO getProductModel(String code) {
        String getProductModelsEndpoint = apiEndpoints.getProductModelsEndpoint() + "/" + code;
        ResponseEntity<ProductModelDTO> response = restTemplate.exchange(
                getProductModelsEndpoint,
                HttpMethod.GET,
                null,
                ProductModelDTO.class
        );
        logger.info("product model: {}", response.getBody());
        return response.getBody();
    }

    public void updateProductModel(ProductModelDTO partialUpdateBody) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf("application/vnd.akeneo.collection+json"));
        HttpEntity<ProductModelDTO> entity = new HttpEntity<>(partialUpdateBody, headers);
        String updateProductModelEndpoint = apiEndpoints.getProductModelsEndpoint();
        restTemplate.exchange(updateProductModelEndpoint, HttpMethod.PATCH, entity, Void.class);
        logger.info("Successfully updated product model: {}", partialUpdateBody.getCode());
    }

    public void createProductModel(ProductModelDTO newProduct) {
        HttpEntity<ProductModelDTO> entity = new HttpEntity<>(newProduct);
        String createProductModelEndpoint = apiEndpoints.getProductModelsEndpoint();
        restTemplate.exchange(createProductModelEndpoint, HttpMethod.POST, entity, ProductModelDTO.class);
        logger.info("created product model: {}", newProduct.getCode());
    }
}
