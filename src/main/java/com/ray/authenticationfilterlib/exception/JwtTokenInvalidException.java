package com.ray.authenticationfilterlib.exception;

public class JwtTokenInvalidException extends RuntimeException {
    public JwtTokenInvalidException(String s) {
        super(s);
    }
}
