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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public String getFamilyVariant() {
        return familyVariant;
    }

    public void setFamilyVariant(String familyVariant) {
        this.familyVariant = familyVariant;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public Map<String, List<AttributeValueDTO>> getValues() {
        return values;
    }

    public void setValues(Map<String, List<AttributeValueDTO>> values) {
        this.values = values;
    }

    public Map<String, AssociationDTO> getAssociations() {
        return associations;
    }

    public void setAssociations(Map<String, AssociationDTO> associations) {
        this.associations = associations;
    }

    public Map<String, QuantifiedAssociationDTO> getQuantifiedAssociations() {
        return quantifiedAssociations;
    }

    public void setQuantifiedAssociations(Map<String, QuantifiedAssociationDTO> quantifiedAssociations) {
        this.quantifiedAssociations = quantifiedAssociations;
    }
}
