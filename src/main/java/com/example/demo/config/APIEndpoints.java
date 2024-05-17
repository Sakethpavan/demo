package com.example.demo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "api")
public class APIEndpoints {

    private String productsEndpoint;

    public String getProductsEndpoint() {
        return productsEndpoint;
    }

    public void setProductsEndpoint(String productsEndpoint) {
        this.productsEndpoint = productsEndpoint;
    }
}
