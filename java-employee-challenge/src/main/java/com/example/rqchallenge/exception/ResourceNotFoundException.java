package com.example.rqchallenge.exception;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String resourceName, String fieldID, String fieldName) {
        super(String.format("%s with %s : %s does not exists", resourceName, fieldID, fieldName));

    }
}
