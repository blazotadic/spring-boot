package com.academy.demo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class CountryDTO implements Serializable {

    private Integer id;
    private String code;
    private String name;

    public CountryDTO(Integer id, String shortCode)
    {
        this.id = id;
        this.code = shortCode;
    }

    public CountryDTO(String name, String shortCode)
    {
        this.name = name;
        this.code = shortCode;
    }
}
