package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AssociationDTO {
    @JsonProperty("groups")
    private List<String> groups;

    @JsonProperty("products")
    private List<String> products;

    @JsonProperty("product_models")
    private List<String> productModels;

    public List<String> getGroups() {
        return groups;
    }

    public void setGroups(List<String> groups) {
        this.groups = groups;
    }

    public List<String> getProducts() {
        return products;
    }

    public void setProducts(List<String> products) {
        this.products = products;
    }

    public List<String> getProductModels() {
        return productModels;
    }

    public void setProductModels(List<String> productModels) {
        this.productModels = productModels;
    }
}
