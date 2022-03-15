package com.academy.demo.security.auth;

import com.academy.demo.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component // customAuth
@RequiredArgsConstructor
public class CustomAuth {

    private final CustomerRepository customerRepository;

    @Value("${auth-key}")
    private String authKey; // LogateAcademy2022


    public boolean hasPermission() {
        return customerRepository.count() <= 3;
    }

    public boolean hasPermissionBasedOnSomething(String providedKey)
    {
        return authKey.equals(providedKey);
    }

}
