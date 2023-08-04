package com.example.rqchallenge.exception;

public class ExternalHttpClientErrorException extends RuntimeException {

    private String message;

    public ExternalHttpClientErrorException(String message) {
        super(message);
    }
}
