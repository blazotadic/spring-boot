package com.academy.demo.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProductDTO {

    private Integer id;
    private String name;
    private Double price;
    private String category;

}
