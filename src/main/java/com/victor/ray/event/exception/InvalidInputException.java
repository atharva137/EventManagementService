package com.victor.ray.event.exception;

import jakarta.persistence.Table;

public class InvalidInputException extends RuntimeException{

    public InvalidInputException(String msg){
        super(msg);
    }
}
