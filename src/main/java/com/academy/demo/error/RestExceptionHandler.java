package com.academy.demo.error;

import com.academy.demo.exception.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorDTO> handleValidationException(ValidationException e)
    {
        List<FieldErrorDTO> customFieldErrors = new ArrayList<>();

        Errors errors = e.getErrors();
        List<FieldError> fieldErrors = errors.getFieldErrors();
        for (FieldError fieldError : fieldErrors)
        {
            FieldErrorDTO fieldErrorDTO = new FieldErrorDTO(
                fieldError.getField(),
                fieldError.getCode(),
                fieldError.getDefaultMessage()
            );
            customFieldErrors.add(fieldErrorDTO);
        }

        ErrorDTO errorDTO = new ErrorDTO(e.getMessage(), customFieldErrors);
        return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);
    }
}
