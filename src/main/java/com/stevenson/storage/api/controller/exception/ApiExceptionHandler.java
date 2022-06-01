package com.stevenson.storage.api.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = {ApiStorageException.class})
    public ResponseEntity<Object> handleApiStorageException(ApiStorageException e){
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        // create payload with details
        ApiException apiException = new ApiException(
                e.getMessage(),
                e,
                badRequest,
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        // return response
        return new ResponseEntity<>(apiException, badRequest);
    }
}
