package com.ray.authenticationfilterlib.exception;

public class JwtTokenSignatureException extends RuntimeException {
    public JwtTokenSignatureException(String s) {
        super(s);
    }
}
