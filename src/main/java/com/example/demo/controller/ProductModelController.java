package com.example.demo.controller;

import com.example.demo.dto.product.ProductModelDTO;
import com.example.demo.exception.CustomException;
import com.example.demo.service.ProductModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/product-models")
public class ProductModelController {

    @Autowired
    private ProductModelService productModelService;

    @GetMapping
    public ResponseEntity<?> getProductModels()  throws CustomException {
        String response = productModelService.getProductModels();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{code}")
    public ResponseEntity<ProductModelDTO> getProductModel(@PathVariable String code) throws CustomException {
        ProductModelDTO responseBody = productModelService.getProductModel(code);
        return ResponseEntity.ok(responseBody);
    }

    @PatchMapping("/{code}")
    public ResponseEntity<Void> updateProductModel(@PathVariable String code, @RequestBody ProductModelDTO partialUpdateBody) throws CustomException {
        productModelService.updateProductModel(code, partialUpdateBody);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<Void> createProductModel(@RequestBody ProductModelDTO newProduct) throws CustomException {
        productModelService.createProductModel(newProduct);
        return ResponseEntity.noContent().build();
    }
}
