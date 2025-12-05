package com.autonoma.exception;
import org.springframework.security.core.AuthenticationException;

public class CredencialesInvalidasException extends AuthenticationException{

    public CredencialesInvalidasException(String msg) {
        super(msg);
    }
}
