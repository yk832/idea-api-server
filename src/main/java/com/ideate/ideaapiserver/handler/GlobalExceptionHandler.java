package com.ideate.ideaapiserver.handler;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Objects;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(GlobalException.class)
    public ResponseEntity<ExceptionResponse> globalRequestException(final GlobalException globalException) {
        return ResponseEntity
                .status(globalException.getStatus())
                .body(new ExceptionResponse(globalException.getMessage(), globalException.getErrorCode()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex) {
        ObjectError objectError = ex.getBindingResult().getAllErrors().get(0);

        return ResponseEntity.badRequest().body(
                new ExceptionResponse(objectError.getDefaultMessage(),
                        objectError.getObjectName().toUpperCase() + "_" +
                                Objects.requireNonNull(objectError.getCode()).toUpperCase()));
    }

    @Getter
    @AllArgsConstructor
    public static class ExceptionResponse {
        private String message;
        private String errorCode;
    }
}
