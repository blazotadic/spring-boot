package com.academy.demo.security.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreateDTO {

    private String username;
    private String password;
    private String passwordConfirm;
    private String firstName;
    private String lastName;


}
