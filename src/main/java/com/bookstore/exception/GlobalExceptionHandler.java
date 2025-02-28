package com.bookstore.exception;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.*;

@Slf4j
@Hidden
@RestControllerAdvice(basePackages = "com.bookstore")
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(code = NOT_FOUND)
    public ResponseEntity<ApiErrorMessage> handleResourceNotFoundException(HttpServletRequest req, ResourceNotFoundException e) {
        logError(e, req.getRequestURI());
        return buildResponse(NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(IllegalArgumentExceptionCustom.class)
    @ResponseStatus(code = BAD_REQUEST)
    public ResponseEntity<ApiErrorMessage> handleIllegalArgumentExceptionCustom(HttpServletRequest req, IllegalArgumentExceptionCustom e) {
        logError(e, req.getRequestURI());
        return buildResponse(BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(DuplicateResourceException.class)
    @ResponseStatus(code = CONFLICT)
    public ResponseEntity<ApiErrorMessage> handleIllegalArgumentExceptionCustom(HttpServletRequest req, DuplicateResourceException e) {
        logError(e, req.getRequestURI());
        return buildResponse(CONFLICT, e.getMessage());
    }

    private void logError(Exception e, String requestURI) {
        final String message = ExceptionUtils.getMessage(e);
        final String stackTrace = ExceptionUtils.getStackTrace(e);

        log.warn("Message {}, RequestURI {}", message, requestURI);
        log.error("StackTrace {}", stackTrace);
    }

    private ResponseEntity<ApiErrorMessage> buildResponse(final HttpStatus httpCode, final String message) {
        Map<String, String> errors = new HashMap<>();
        errors.put("message", message);
        ApiErrorMessage apiErrorMessage = new ApiErrorMessage(errors);
        return ResponseEntity.status(httpCode).body(apiErrorMessage);
    }
}
