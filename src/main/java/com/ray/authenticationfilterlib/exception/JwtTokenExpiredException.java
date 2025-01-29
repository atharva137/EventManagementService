package com.ray.authenticationfilterlib.exception;

public class JwtTokenExpiredException extends RuntimeException {
    public JwtTokenExpiredException(String s) {
        super(s);
    }
}
