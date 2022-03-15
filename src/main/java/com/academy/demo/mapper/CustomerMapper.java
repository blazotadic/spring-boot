package com.academy.demo.mapper;

import com.academy.demo.dto.CustomerWithAddressDTO;
import com.academy.demo.entity.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;


@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface CustomerMapper
{
    @Mapping(source = "fullName", target = "name")
    CustomerWithAddressDTO toAddressDTO(Customer customer);
}
