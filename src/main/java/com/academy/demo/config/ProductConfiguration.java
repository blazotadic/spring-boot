package com.academy.demo.config;

import com.academy.demo.entity.Product;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ProductConfiguration {

    // PasswordEncoder (Spring Security)

    @Bean("productOne")
    public Product product()
    {
        Product product = new Product();
        product.setName("Product1");
        product.setCategory("Category1");

        return product;
    }

    @Bean("productTwo")
    public Product productTwo()
    {
        Product product = new Product();
        product.setName("Product2");
        product.setCategory("Category2");

        return product;
    }
}
