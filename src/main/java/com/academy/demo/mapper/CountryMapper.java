package com.academy.demo.mapper;

import com.academy.demo.dto.CountryDTO;
import com.academy.demo.entity.Country;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
// @Component
public interface CountryMapper
{
    @Mapping(source = "code", target = "shortCode", defaultValue = "ME") // code (CountryDTO) -> shortCode (Country)
    Country convertToEntity(CountryDTO countryDTO);
}
