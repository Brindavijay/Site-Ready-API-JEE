package com.icon.exception;


public class ApplicationConfigException extends RuntimeException {

    private String message;

    public ApplicationConfigException(String message, Exception e){
        super(message, e);
    }

}
