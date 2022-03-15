package com.academy.demo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "fake-store-api")
public class FakeStoreApiConfiguration {

    private String baseUrl;
    private String productPart;
    private String categoryPart;
    private String cartPart;

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getProductPart() {
        return productPart;
    }

    public void setProductPart(String productPart) {
        this.productPart = productPart;
    }

    public String getCategoryPart() {
        return categoryPart;
    }

    public void setCategoryPart(String categoryPart) {
        this.categoryPart = categoryPart;
    }

    public String getCartPart() {
        return cartPart;
    }

    public void setCartPart(String cartPart) {
        this.cartPart = cartPart;
    }
}
