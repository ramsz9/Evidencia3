package com.tareas.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

import com.tareas.dto.ErrorMessageDto;

@ControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(BusinessException.class)
    public ResponseEntity<?> businessExceptionHandler(BusinessException businessException) {
        ErrorMessageDto errorMessageDto = ErrorMessageDto.builder()
                .httpStatus(HttpStatus.BAD_REQUEST.toString())
                .message(businessException.getMessage())
                .build();
        return new ResponseEntity<>(errorMessageDto, HttpStatus.BAD_REQUEST);
    }

}
