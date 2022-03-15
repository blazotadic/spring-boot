package com.academy.demo.config;

import com.academy.demo.entity.Category;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CategoryConfiguration {

    @Bean
    public Category category()
    {
        Category category = new Category();
        category.setName("Category45");

        return category;
    }

}
