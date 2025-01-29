package com.victor.ray.event.exception;

public class EventServiceException extends RuntimeException{

    public EventServiceException (String msg, Throwable ex){
        super(msg,ex);
    }
    public EventServiceException (String msg){
        super(msg);
    }
}
