package com.example.demo.service;

import com.example.demo.config.APIEndpoints;
import com.example.demo.dto.product.ProductDTO;
import com.example.demo.dto.product.ProductModelDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
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
        logger.info("product: {}", response.getBody());
        return response.getBody();
    }

    public void updateProductModel(String code, ProductDTO partialUpdateBody) {
        HttpEntity<ProductDTO> entity = new HttpEntity<>(partialUpdateBody);
        String updateProductModelEndpoint = apiEndpoints.getProductModelsEndpoint() + "/" + code;
        restTemplate.exchange(updateProductModelEndpoint, HttpMethod.PATCH, entity, Void.class);
        logger.info("Successfully updated product: {}", code);
    }

    public void createProductModel(ProductDTO newProduct) {
        HttpEntity<ProductDTO> entity = new HttpEntity<>(newProduct);
        String createProductModelEndpoint = apiEndpoints.getProductModelsEndpoint();
        restTemplate.exchange(createProductModelEndpoint, HttpMethod.POST, entity, ProductDTO.class);
        logger.info("created product: {}", newProduct.getIdentifier());
    }
}
