package com.academy.demo.dto.fakestore;

import com.academy.demo.dto.CategoryDTO;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class ProductWithCategoriesDTO {

    private Integer id;
    private String name;

    // @JsonProperty("category-list")
    private List<CategoryDTO> categories = new ArrayList<>(); // []


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CategoryDTO> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryDTO> categories) {
        this.categories = categories;
    }

    @Override
    public String toString()
    {
        return "ProductWithCategoriesDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", categories=" + categories +
                '}';
    }
}
