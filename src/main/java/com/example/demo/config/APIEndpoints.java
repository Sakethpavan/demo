package com.example.demo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "api")
public class APIEndpoints {

    private String productsEndpoint;

    private String productModelsEndpoint;

    public String getProductsEndpoint() {
        return productsEndpoint;
    }

    public void setProductsEndpoint(String productsEndpoint) {
        this.productsEndpoint = productsEndpoint;
    }

    public String getProductModelsEndpoint() {
        return productModelsEndpoint;
    }

    public void setProductModelsEndpoint(String productModelsEndpoint) {
        this.productModelsEndpoint = productModelsEndpoint;
    }
}
