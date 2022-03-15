package com.academy.demo.service;

import com.academy.demo.dto.ProductDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductFakerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductFakerService.class);

    public List<ProductDTO> findAll()
    {
        ProductDTO product1 = new ProductDTO();
        product1.setId(1);
        product1.setName("Proizvod 1");
        product1.setPrice(100.00D);
        product1.setCategory("Kategorija 1");

        ProductDTO product2 = new ProductDTO();
        product2.setId(2);
        product2.setName("Proizvod 2");
        product2.setPrice(245.12D);
        product2.setCategory("Kategorija 2");

        List<ProductDTO> products = new ArrayList<>();
        products.add(product1);
        products.add(product2);

        return products;
    }

    public Optional<ProductDTO> findOneByIdOptional(Integer productId)
    {
        return findAll()
                .stream()
                .filter(product -> product.getId().equals(productId))
                .findFirst();
    }

    public ProductDTO findOneById(Integer productId)
    {
        List<ProductDTO> allProducts = findAll();
        for (ProductDTO productDTO : allProducts)
        {
            if (productDTO.getId().equals(productId)) {
                return productDTO;
            }
        }
        return null;
    }

    public List<ProductDTO> findByCategory(String category)
    {
        return findAll()
                .stream()
                .filter(product -> product.getCategory().equals(category))
                .collect(Collectors.toList());
    }

    public void insert(ProductDTO product)
    {
        LOGGER.info("Inserting product into DB... Product data: {}", product);
        product.setId(22);
    }

}
