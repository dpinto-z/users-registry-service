package com.users.registry.service.controller.advice;

import com.users.registry.service.domain.response.ErrorResponse;
import com.users.registry.service.domain.response.Errors;
import com.users.registry.service.exception.RegisterNotAllowedException;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.nio.file.AccessDeniedException;
import java.security.SignatureException;
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

    //The username or password is incorrect
    @ExceptionHandler(value = BadCredentialsException.class)
    public ResponseEntity<?> badCredentialsExceptionHandler(BadCredentialsException e) {
        log.info("action=controllerException, badCredentialsExceptionHandler=", e);
        return buildResponseEntity(
                e.getMessage()+" Incorrect Email or Password", HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = ExpiredJwtException.class)
    public ResponseEntity<?> expiredJwtExceptionHandler(ExpiredJwtException e) {
        log.info("action=controllerException, expiredJwtExceptionHandler=", e);
        return buildResponseEntity(
                e.getMessage()+" The JWT token has expired", HttpStatus.UNAUTHORIZED);
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
