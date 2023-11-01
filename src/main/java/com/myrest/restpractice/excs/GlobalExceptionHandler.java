package com.myrest.restpractice.excs;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({
            GlobalException.class
    })
    public ResponseEntity<GlobalError> documentConstructorTypeNameExistsException(GlobalException ex) {
        return ResponseEntity
                .status(ex.getStatus())
                .body(new GlobalError(ex.getStatus(), ex.getMessage()));
    }
}
