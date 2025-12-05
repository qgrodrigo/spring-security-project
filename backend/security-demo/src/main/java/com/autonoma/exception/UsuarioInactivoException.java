package com.autonoma.exception;
import org.springframework.security.core.AuthenticationException;

public class UsuarioInactivoException extends AuthenticationException{
    public UsuarioInactivoException(String msg) {
        super(msg);
    }
}
