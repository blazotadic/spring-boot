package com.academy.demo.service;

import com.academy.demo.repository.CategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryService.class);

    @Autowired
    private RestTemplate restTemplate; // http rest template

    @Autowired
    @Qualifier("secureRestTemplate")
    private RestTemplate secureRestTemplate;

    @Autowired
    private CategoryRepository categoryRepository;


    /**
     * Getting all categories from fake REST API
     *
     * @return List of category names
     */
    public List<String> findAllExternal()
    {
        String url = "https://fakestoreapi.com/products/categories";
        try
        {
            ParameterizedTypeReference<List<String>> typeReference = new ParameterizedTypeReference<>() {};

            ResponseEntity<List<String>> responseEntity = secureRestTemplate.exchange(
                url, HttpMethod.GET, null, typeReference
            );

            LOGGER.info("Response body: {}", responseEntity.getBody());
            LOGGER.info("Response Http Status Code: {}", responseEntity.getStatusCodeValue()); // 200

            return responseEntity.getBody();
        }
        catch (Exception e)
        {
            LOGGER.error("Error has been occurred. Message: {}", e.getMessage());
            return new ArrayList<>();
        }
    }
}
