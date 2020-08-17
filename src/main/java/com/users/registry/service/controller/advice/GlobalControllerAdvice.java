package com.users.registry.service.controller.advice;


import com.users.registry.service.domain.response.ErrorResponse;
import com.users.registry.service.domain.response.Errors;
import com.users.registry.service.exception.RegisterNotAllowedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Collections;
import java.util.List;

@Slf4j
@ControllerAdvice
public class GlobalControllerAdvice {


    @ExceptionHandler(value = {Exception.class, DataAccessException.class})
    public ResponseEntity<?> genericExceptionHandler(Exception e) {
        log.info("action=controllerException, genericException=", e);
        return buildResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<?> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        log.info("action=controllerException, methodArgumentNotValidExceptionHandler=", e);
        return buildResponseEntity(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
    }


    @ExceptionHandler(value = RegisterNotAllowedException.class)
    public ResponseEntity<?> registerNotAllowedExceptionHandler(RegisterNotAllowedException e) {
        log.info("action=controllerException, registerNotAllowedExceptionHandler=", e);
        return buildResponseEntity(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
    }


    private ResponseEntity<?> buildResponseEntity(String exceptionMessage, HttpStatus httpStatus) {
        List<Errors> errorsList = Collections.singletonList(new Errors(exceptionMessage));
        return new ResponseEntity<>(new ErrorResponse(httpStatus.value(), errorsList), httpStatus);
    }

}
