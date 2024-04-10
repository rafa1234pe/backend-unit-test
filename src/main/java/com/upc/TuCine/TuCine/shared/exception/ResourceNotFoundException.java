package com.upc.TuCine.TuCine.shared.exception;

public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException() {
        super();
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String resourceName, Integer resourceId) {
        super(String.format("%s with id %d not found.", resourceName, resourceId));
    }
}
