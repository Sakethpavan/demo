package com.example.demo.controller;

import com.example.demo.config.APIEndpoints;
import com.example.demo.dto.ProductDTO;
import com.example.demo.exception.CustomException;
import com.example.demo.service.AuthService;
import com.example.demo.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
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

    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public ResponseEntity<?> getProducts()  throws CustomException {
        String response = productService.getProducts();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/products/{code}")
    public ResponseEntity<ProductDTO> getProduct(@PathVariable String code) throws CustomException{
        ProductDTO responseBody = productService.getProduct(code);
        return ResponseEntity.ok(responseBody);
    }

    @PatchMapping("/products/{code}")
    public ResponseEntity<?> updateProduct(@PathVariable String code, @RequestBody ProductDTO partialUpdateBody) {
        HttpEntity<ProductDTO> entity = new HttpEntity<>(partialUpdateBody);
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
        HttpEntity<ProductDTO> entity = new HttpEntity<>(body);
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
