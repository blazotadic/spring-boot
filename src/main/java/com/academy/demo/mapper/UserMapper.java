package com.academy.demo.mapper;

import com.academy.demo.dto.UserWithDetailDTO;
import com.academy.demo.entity.User;
import com.academy.demo.security.dto.UserCreateDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper
{
    UserWithDetailDTO toUserWithDetailDTO(User user);
    User fromUserWithDetailDTO(UserWithDetailDTO userWithDetailDTO);
    User fromUserCreateToEntity(UserCreateDTO userCreateDTO);
}
