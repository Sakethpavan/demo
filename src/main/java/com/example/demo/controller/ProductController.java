package com.example.demo.controller;

import com.example.demo.dto.product.ProductDTO;
import com.example.demo.exception.CustomException;
import com.example.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<?> getProducts()  throws CustomException {
        String response = productService.getProducts();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{code}")
    public ResponseEntity<ProductDTO> getProduct(@PathVariable String code) throws CustomException {
        ProductDTO responseBody = productService.getProduct(code);
        return ResponseEntity.ok(responseBody);
    }

    @PatchMapping("/{code}")
    public ResponseEntity<Void> updateProduct(@PathVariable String code, @RequestBody ProductDTO partialUpdateBody) throws CustomException {
        productService.updateProduct(code, partialUpdateBody);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<Void> createProduct(@RequestBody ProductDTO newProduct) throws CustomException {
        productService.createProduct(newProduct);
        return ResponseEntity.noContent().build();
    }
}
