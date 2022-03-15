package com.academy.demo.security.controller;

import com.academy.demo.exception.ValidationException;
import com.academy.demo.security.dto.UserCreateDTO;
import com.academy.demo.security.dto.UserLoginDTO;
import com.academy.demo.security.jwt.JwtTokenProvider;
import com.academy.demo.security.validator.UserCreateValidator;
import com.academy.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/authenticate")
public class AuthController {

    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    private final UserCreateValidator userCreateValidator;
    private final UserService userService;


    @PostMapping(value = "login")
    public ResponseEntity<Map<String, String>> login(@RequestBody UserLoginDTO userLoginDTO)
    {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
            userLoginDTO.getUsername(), userLoginDTO.getPassword()
        );
        try
        {
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication); // thread local!!!

            String token = jwtTokenProvider.createToken(authentication);
            return new ResponseEntity<>(Collections.singletonMap("token", token), HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping(value = "register")
    public ResponseEntity<Void> register(@RequestBody UserCreateDTO userCreateDTO) throws ValidationException
    {
        Errors errors = new BeanPropertyBindingResult(userCreateDTO, "userCreateDTO");
        ValidationUtils.invokeValidator(userCreateValidator, userCreateDTO, errors);

        if (errors.hasErrors()) {
            throw new ValidationException("Register user validation failed!", errors);
        }

        userService.register(userCreateDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
