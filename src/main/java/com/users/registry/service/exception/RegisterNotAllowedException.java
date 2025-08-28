package com.users.registry.service.exception;

import lombok.Getter;

@Getter
public class RegisterNotAllowedException extends RuntimeException {

    private final String customMessage;

    public RegisterNotAllowedException(String customMessage) {
        super(customMessage);
        this.customMessage = customMessage;
    }
}
