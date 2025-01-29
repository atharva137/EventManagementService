package com.ray.authenticationfilterlib.exception;

public class JwtTokenMalformedException extends RuntimeException {
    public JwtTokenMalformedException(String s) {

        super(s);
    }
}
