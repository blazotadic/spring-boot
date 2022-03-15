package com.academy.demo.mapper;

import com.academy.demo.dto.CityDTO;
import com.academy.demo.entity.City;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CityMapper
{
    City mapToEntity(CityDTO cityDTO);
    CityDTO toDTO(City city);
}
