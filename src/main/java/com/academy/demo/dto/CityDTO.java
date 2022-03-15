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
public class CityDTO implements Serializable {

    private Integer id;
    private String name;
    private CountryDTO country;

}
