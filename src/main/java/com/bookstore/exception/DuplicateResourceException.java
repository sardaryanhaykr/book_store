package com.bookstore.exception;

public class DuplicateResourceException extends RuntimeException {
    private static final String RESOURCE_ALREADY_EXIST = "%s already exists";

    public DuplicateResourceException(String resourceName) {
        super(buildMessage(resourceName));
    }

    private static String buildMessage(String resourceName) {
        return String.format(RESOURCE_ALREADY_EXIST, resourceName);
    }
}
