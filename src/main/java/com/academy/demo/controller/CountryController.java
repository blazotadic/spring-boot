package com.academy.demo.controller;

import com.academy.demo.dto.CountryDTO;
import com.academy.demo.entity.Country;
import com.academy.demo.service.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/country")
public class CountryController {

    private final CountryService countryService;

    @GetMapping
    public ResponseEntity<Page<Country>> findAllWithPageable(Pageable pageable)
    {
        Page<Country> countryPage = countryService.findPage(pageable);
        return new ResponseEntity<>(countryPage, HttpStatus.OK);
    }

    @GetMapping(value = "/pageable")
    public ResponseEntity<List<Country>> findAllPageable(Pageable pageable)
    {
        List<Country> countries = countryService.findAllPageable(pageable);
        return new ResponseEntity<>(countries, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> store(@RequestBody CountryDTO countryDTO)
    {
        if (countryDTO.getId() != null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        countryService.save(countryDTO);
        return new ResponseEntity<>(HttpStatus.CREATED); // 201!
    }

    @PutMapping(value = "{id}")
    public ResponseEntity<Void> update(@PathVariable Integer id, @RequestBody CountryDTO countryDTO)
    {
        countryService.update(id, countryDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id)
    {
        countryService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204!
    }
}
