package com.academy.demo.service;

import com.academy.demo.config.FakeStoreApiConfiguration;
import com.academy.demo.dto.ProductDTO;
import com.academy.demo.dto.fakestore.ProductFakeDTO;
import com.academy.demo.dto.fakestore.ProductFakeInsertDTO;
import com.academy.demo.entity.ProductEntity;
import com.academy.demo.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductService.class);

    @Autowired
    private ProductFakerService fakerService;

    @Autowired
    @Qualifier("secureRestTemplate")
    private RestTemplate secureRestTemplate;

    @Autowired
    private FakeStoreApiConfiguration fakeStoreApiConfiguration;

    @Autowired
    private ProductRepository productRepository;


    public List<ProductDTO> findAll() {
        return fakerService.findAll();
    }

    public Optional<ProductDTO> findOneByIdOptional(Integer productId) {
       return fakerService.findOneByIdOptional(productId);
    }

    public ProductDTO findOneById(Integer productId) {
        return fakerService.findOneById(productId);
    }

    public List<ProductDTO> findByCategory(String category)
    {
        return findAll()
            .stream()
            .filter(product -> product.getCategory().equals(category))
            .collect(Collectors.toList());
    }

    public void insert(ProductDTO product) {
        fakerService.insert(product);
    }

    public List<ProductFakeDTO> findAllFake()
    {
        String url = "https://fakestoreapi.com/products";
        try
        {
            ParameterizedTypeReference<List<ProductFakeDTO>> typeReference = new ParameterizedTypeReference<>() {};

            ResponseEntity<List<ProductFakeDTO>> responseEntity = secureRestTemplate.exchange(
                url, HttpMethod.GET, null, typeReference
            );

            return responseEntity.getBody();
        }
        catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public Optional<ProductFakeInsertDTO> insertFake(ProductFakeInsertDTO productFakeInsert)
    {
        String url = fakeStoreApiConfiguration.getBaseUrl().concat(fakeStoreApiConfiguration.getProductPart());
        try
        {
            HttpEntity<ProductFakeInsertDTO> requestEntity = new HttpEntity<>(productFakeInsert);
            ResponseEntity<ProductFakeInsertDTO> responseEntity = secureRestTemplate.exchange(
                url, HttpMethod.POST, requestEntity, ProductFakeInsertDTO.class
            );
            return Optional.ofNullable(responseEntity.getBody());
        }
        catch (Exception e)
        {
            LOGGER.error("Error has been occurred while inserting new product. Message: {}", e.getMessage());
            return Optional.empty();
        }
    }

    public Optional<ProductFakeDTO> deleteFakeById(Integer id)
    {
        String url = fakeStoreApiConfiguration.getBaseUrl() + "/products/" + id;
        try
        {
            ResponseEntity<ProductFakeDTO> responseEntity = secureRestTemplate.exchange(
                url, HttpMethod.DELETE, null, ProductFakeDTO.class
            );
            return Optional.ofNullable(responseEntity.getBody());
        }
        catch (Exception e)
        {
            LOGGER.error("Error has been occurred while deleting product. Message: {}", e.getMessage());
            return Optional.empty();
        }
    }

    public List<ProductEntity> search(String term)
    {
        // WHERE NAME LIKE :term OR description LIKE :term
        return productRepository.findByNameStartingWithOrDescriptionStartingWith(term, term);
    }
}
