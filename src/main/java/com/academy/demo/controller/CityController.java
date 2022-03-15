package com.academy.demo.controller;

import com.academy.demo.dto.CityDTO;
import com.academy.demo.entity.City;
import com.academy.demo.exception.CustomSQLException;
import com.academy.demo.exception.ValidationException;
import com.academy.demo.service.CityService;
import com.academy.demo.validator.CityCreateValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/city")
public class CityController {

    private final CityService cityService;
    private final CityCreateValidator cityCreateValidator;


    @PostMapping
    public ResponseEntity<Void> create(@RequestBody CityDTO cityDTO) throws ValidationException
    {
        // MapPropertyBindingResult
        Errors errors = new BeanPropertyBindingResult(cityDTO, "cityDTO");
        ValidationUtils.invokeValidator(cityCreateValidator, cityDTO, errors);

        if (errors.hasErrors())
        {
            throw new ValidationException(
                "Validation error has been occurred!",
                errors
            );
        }

        cityService.save(cityDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<CityDTO> getById(@PathVariable Integer id)
    {
        CityDTO city = cityService.findById(id);
        return new ResponseEntity<>(city, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<CityDTO>> all()
    {
        List<CityDTO> cities = cityService.findAll();
        return new ResponseEntity<>(cities, HttpStatus.OK);
    }
}
