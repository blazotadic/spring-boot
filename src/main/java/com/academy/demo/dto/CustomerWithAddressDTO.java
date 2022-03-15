package com.academy.demo.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString
public class CustomerWithAddressDTO {

    private Integer id;
    private String name;
    private String phone;
    private Set<AddressDTO> addresses = new HashSet<>();
}
