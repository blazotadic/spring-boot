package com.academy.demo.service;

import com.academy.demo.config.TwitterConfiguration;
import com.academy.demo.entity.Category;
import com.academy.demo.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class CampaignService {

    @Value("${db-url}")
    private String dbUrl; // test123

    @Autowired
    private TwitterConfiguration twitterConfiguration;

    @Autowired
    private Category category; // property field injection...

    @Autowired
    private Environment environment;

    // injection through constructor... (constructor injection!)
//    private Category category;
//    private Product productOne;
//    private Product productTwo;
//
//    public CampaignService(Category category,
//                           @Qualifier("productOne") Product productOne,
//                           @Qualifier("productTwo") Product productTwo)
//    {
//        this.category = category;
//        this.productOne = productOne;
//        this.productTwo = productTwo;
//    }

    public void config()
    {
        String twitterUrl = environment.getProperty("twitter-api.url", "default-value-of-url");
        String dbUrlFromConfig = environment.getProperty("db-url");
        String datasourcePassword = environment.getProperty("spring.datasource.password");
        String authToken = environment.getRequiredProperty("twitter-api.authentication-token");
    }

    public void testCategory()
    {
        category.showProductData();

//        twitterConfiguration.getUsername();
//        twitterConfiguration.getPassword();
//        twitterConfiguration.getUrl();
    }

    // setter injection...
    //    private Category category;
//    private Product product;

//    @Autowired
//    public void setCategory(Category category) {
//        this.category = category;
//    }
//
//    @Autowired
//    @Qualifier("productOne")
//    public void setProduct(Product product) {
//        this.product = product;
//    }
}
