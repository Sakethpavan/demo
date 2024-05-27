package com.example.demo.dto.product;

import com.example.demo.dto.AssociationDTO;
import com.example.demo.dto.AttributeValueDTO;
import com.example.demo.dto.QuantifiedAssociationDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductModelDTO {
    @JsonProperty("code")
    private String code;

    @JsonProperty("family")
    private String family;

    @JsonProperty("family_variant")
    private String familyVariant;

    @JsonProperty("parent")
    private String parent;

    @JsonProperty("categories")
    private List<String> categories;

    @JsonProperty("values")
    private Map<String, List<AttributeValueDTO>> values;

    @JsonProperty("associations")
    private Map<String, AssociationDTO> associations;

    @JsonProperty("quantified_associations")
    private Map<String, QuantifiedAssociationDTO> quantifiedAssociations;

    // Getters and Setters
}
