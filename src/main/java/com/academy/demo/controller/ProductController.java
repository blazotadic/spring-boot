package com.academy.demo.controller;

import com.academy.demo.dto.ProductDTO;
import com.academy.demo.dto.fakestore.ProductFakeDTO;
import com.academy.demo.dto.fakestore.ProductFakeInsertDTO;
import com.academy.demo.dto.fakestore.ProductWithCategoriesDTO;
import com.academy.demo.dto.fakestore.ProductWithDataDTO;
import com.academy.demo.entity.ProductEntity;
import com.academy.demo.search.ProductSearch;
import com.academy.demo.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService productService;

    /**
     * Getting all products | GET : /api/product
     *
     * @return ResponseEntity with List of ProductDTO objects and Http Status Code 200
     */
    @GetMapping
    public ResponseEntity<List<ProductDTO>> all()
    {
        List<ProductDTO> products = productService.findAll();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    /**
     * Getting product by unique identifier | GET : /api/product/{id}
     *
     * @param productId product identifier
     * @return ResponseEntity with ProductDTO object and Http Status Code (200 | 400)
     */
    @GetMapping(value = "{id}")
    public ResponseEntity<ProductDTO> one(@PathVariable(value = "id") Integer productId)
    {
        ProductDTO product = productService.findOneById(productId);
        return product != null
            ? new ResponseEntity<>(product, HttpStatus.OK)
            : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Getting products by category | GET : /api/product/by-category?category=####
     *
     * @param category category name as query parameter
     * @return ResponseEntity with List of ProductDTO objects and Http Status Code 200
     */
    @GetMapping(value = "by-category")
    public ResponseEntity<List<ProductDTO>> byCategory(@RequestParam(value = "category") String category)
    {
        List<ProductDTO> products = productService.findByCategory(category);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    /**
     * Getting products by multiple categories
     *
     * @param categories category as query parameters with multiple values
     * @return ResponseEntity with List of ProductDTO objects and Http Status Code 200
     */
    @GetMapping(value = "by-categories")
    public ResponseEntity<List<ProductDTO>> byCategories(@RequestParam(value = "category") List<String> categories)
    {
        log.info("Categories: {}", categories);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Getting products by unknown parameters | GET : /api/product/search/unknown
     *
     * @param parameters undefined query parameters
     * @return ResponseEntity with List of ProductDTO objects and Http Status Code 200
     */
    @GetMapping(value = "search/unknown")
    public ResponseEntity<List<ProductDTO>> searchByUnknown(@RequestParam Map<String, Object> parameters)
    {
        log.info("Parameters: {}", parameters);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Getting products by known parameters | GET : /api/product/search
     *
     * @param productSearch defined query parameters
     * @return ResponseEntity with List of ProductDTO objects and Http Status Code 200
     */
    @GetMapping(value = "search")
    public ResponseEntity<List<ProductDTO>> search(ProductSearch productSearch)
    {
        log.info("Product search: {}", productSearch);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Inserting new product | POST: /api/product
     *
     * @param product given product payload
     * @return ResponseEntity with Http Status Code (201)
     */
    @PostMapping
    public ResponseEntity<Void> insert(@RequestBody ProductDTO product)
    {
        log.info("Product to be stored: {}", product);

        productService.insert(product);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * Inserting products with categories | POST: /api/product/with-categories
     *
     * @param product given product payload
     * @return ResponseEntity with Http Status Code (201)
     */
    @PostMapping(value = "with-categories")
    public ResponseEntity<Void> insertWithCollectionOfCategories(@RequestBody ProductWithCategoriesDTO product)
    {
        log.info("Product with categories: {}", product);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * Inserting new collection of products with categories | POST: /api/product/collection-with-categories
     *
     * @param products collection of products with categories
     * @return ResponseEntity with Http Status Code (201)
     */
    @PostMapping(value = "collection-with-categories")
    public ResponseEntity<Void> insertWithCollectionOfCategories(@RequestBody List<ProductWithCategoriesDTO> products)
    {
        log.info("Products with categories: {}", products);
        return new ResponseEntity<>(HttpStatus.CREATED); // 201
    }

    /**
     * Inserting product with data prefix | POST: /api/product/collection-with-prefix
     *
     * @param productMap product with prefix
     * @return ResponseEntity with Http Status Code (201)
     */
    @PostMapping(value = "collection-with-prefix")
    public ResponseEntity<Void> insertWithCollectionOfCategoriesWithPrefix(@RequestBody ProductWithDataDTO productMap)
    {
        log.info("Products with categories: {}", productMap);
        return new ResponseEntity<>(HttpStatus.CREATED); // 201
    }

    /**
     * Inserting product with provided headers | POST: /api/product/insert-with-headers
     *
     * @param product given product payload
     * @param auth authentication header
     * @param token token header
     * @return ResponseEntity with Http Status Code (201)
     */
    @PostMapping(value = "insert-with-headers")
    public ResponseEntity<Void> insertWithHeaders(@RequestBody ProductWithCategoriesDTO product,
                                                  @RequestHeader(value = "Authentication", required = false) String auth,
                                                  @RequestHeader(value = "token") String token)
    {
        log.info("Request header: Authentication: {} - Token: {}", auth, token);
        log.info("Request payload - Product: {}", product);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * Inserting product with bulk header mapping | POST: /api/product/insert-with-headers-bulk
     *
     * @param product given product payload with categories
     * @param headers request headers
     * @return ResponseEntity with Http Status Code (201)
     */
    @PostMapping(value = "insert-with-headers-bulk")
    public ResponseEntity<Void> insertWithHeadersBulk(@RequestBody ProductWithCategoriesDTO product,
                                                      @RequestHeader Map<String, String> headers)
    {
        log.info("Request headers: {}", headers);
        log.info("Product payload: {}", product);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * Inserting product with multi-value header mapping | POST: /api/product/insert-with-headers-bulk-multi-value-map
     *
     * @param product product payload
     * @param values request headers with same keys (multiple values)
     * @return ResponseEntity with Http Status Code (201)
     */
    @PostMapping(value = "insert-with-headers-bulk-multi-value-map")
    public ResponseEntity<Void> insertWithHeadersBulkMultiValue(@RequestBody ProductWithCategoriesDTO product,
                                                                @RequestHeader MultiValueMap<String, List<String>> values)
    {
        log.info("Multi value headers: {}", values);
        log.info("Product with categories: {}", product);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * Inserting product by mapping headers with HttpHeaders class
     * POST: /api/product/insert-with-headers-bulk-dedicated-class-mapping
     *
     * @param product product payload
     * @param httpHeaders Http Request Headers
     * @return ResponseEntity with Http Status Code (201)
     */
    @PostMapping(value = "insert-with-headers-bulk-dedicated-class-mapping")
    public ResponseEntity<Void> insertWithHeadersBulkDedicatedClass(@RequestBody ProductWithCategoriesDTO product,
                                                                    @RequestHeader HttpHeaders httpHeaders)
    {
        log.info("Http headers: {}", httpHeaders);
        return new ResponseEntity<>(HttpStatus.CREATED); // 201
    }

    /**
     * Getting products with response headers | GET: /api/product/respond-with-headers
     *
     * @return ResponseEntity with response payload, Http Headers and Http Status Code (200)
     */
    @GetMapping(value = "respond-with-headers")
    public ResponseEntity<Map<String, String>> getWithResponseHeaders()
    {
        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("message", "This is response body");

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Test-Header", "Test123");

        return new ResponseEntity<>(responseBody, responseHeaders, HttpStatus.OK);
    }

    /**
     * Getting fake external products | POST: /api/product/fake
     *
     * @return ResponseEntity with List of ProductFakeDTO Objects and Http Status Code (200)
     */
    @GetMapping(value = "fake")
    public ResponseEntity<List<ProductFakeDTO>> getAllFakeProducts()
    {
        List<ProductFakeDTO> products = productService.findAllFake();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @PostMapping(value = "fake")
    public ResponseEntity<ProductFakeInsertDTO> insertingFake(@RequestBody ProductFakeInsertDTO product)
    {
        Optional<ProductFakeInsertDTO> optionalProduct = productService.insertFake(product);
        return optionalProduct
            .map(productFakeInsertDTO -> new ResponseEntity<>(productFakeInsertDTO, HttpStatus.CREATED))
            .orElse(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @DeleteMapping(value = "fake/{id}")
    public ResponseEntity<ProductFakeDTO> deleteFakeById(@PathVariable Integer id)
    {
        Optional<ProductFakeDTO> optionalProduct = productService.deleteFakeById(id);
        return optionalProduct
            .map(deletedProduct -> new ResponseEntity<>(deletedProduct, HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @GetMapping(value = "search/by-name-or-desc")
    public ResponseEntity<List<ProductEntity>> searchByNameOrDesc(@RequestParam(value = "term") String term)
    {
        List<ProductEntity> products = productService.search(term);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
}
