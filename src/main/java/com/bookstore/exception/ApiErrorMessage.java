package com.bookstore.exception;


import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class ApiErrorMessage {
    private Map<String, String> errors;

    public ApiErrorMessage(Map<String, String> errors) {
        this.errors = errors;
    }

}
