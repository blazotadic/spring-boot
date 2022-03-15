package com.academy.demo.mapper;

import com.academy.demo.dto.RoleDTO;
import com.academy.demo.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoleMapper
{
    RoleDTO toDTO(Role role);
    Role toEntity(RoleDTO roleDTO);
}
