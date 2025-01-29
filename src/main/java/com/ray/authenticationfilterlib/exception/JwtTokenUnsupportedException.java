package com.ray.authenticationfilterlib.exception;

public class JwtTokenUnsupportedException extends RuntimeException {
    public JwtTokenUnsupportedException(String s) {
        super(s);
    }
}
