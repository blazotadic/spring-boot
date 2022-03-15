package com.academy.demo.controller;

import com.academy.demo.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/category")
public class CategoryController {

    private final CategoryService categoryService;

    /**
     * Getting all categories from external store | GET : /api/category
     *
     * @return ResponseEntity with List of categories and Http Status Code (200)
     */
    @GetMapping
    public ResponseEntity<List<String>> getAll()
    {
        List<String> categories = categoryService.findAllExternal();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }
}
