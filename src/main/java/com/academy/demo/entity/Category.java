package com.academy.demo.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class Category {

    private static final Logger LOGGER = LoggerFactory.getLogger(Category.class);

    @Autowired
    @Qualifier("productTwo")
    private Product product;


    private String name;

    public void showProductData() {
        LOGGER.info("Product name: {} | category: {}", product.getName(), product.getCategory());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
