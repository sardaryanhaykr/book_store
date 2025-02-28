package com.bookstore.exception;

public class ResourceNotFoundException extends RuntimeException {
    private static final String RESOURCE_NOT_FOUND_MESSAGE = "%s not found";

    public ResourceNotFoundException(String resourceName) {
        super(buildMessage(resourceName));
    }

    private static String buildMessage(String resourceName) {
        return String.format(RESOURCE_NOT_FOUND_MESSAGE, resourceName);
    }
}
