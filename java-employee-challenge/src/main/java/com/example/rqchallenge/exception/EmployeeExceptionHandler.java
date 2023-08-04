package com.example.rqchallenge.exception;

import com.example.rqchallenge.RqChallengeApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class EmployeeExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeExceptionHandler.class);

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetail> handleResourceNotFoundException(Exception exception, WebRequest request) {
        ErrorDetail errorDetail = new ErrorDetail(LocalDateTime.now(),
                exception.getMessage(),
                "EMPLOYEE_NOT_FOUND",
                request.getDescription(false));
        LOGGER.error(exception.getMessage());
        return new ResponseEntity<>(errorDetail, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ExternalHttpClientErrorException.class)
    public ResponseEntity<ErrorDetail> handleExternalHttpClientErrorException(Exception exception, WebRequest request) {
        ErrorDetail errorDetail = new ErrorDetail(LocalDateTime.now(),
                exception.getMessage(),
                "EXTERNAL_SERVICE_DOWN",
                request.getDescription(false));
        LOGGER.error(exception.getMessage());
        return new ResponseEntity<>(errorDetail, HttpStatus.TOO_MANY_REQUESTS);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorDetail> handleNoSuchElementException(Exception exception, WebRequest request) {
        ErrorDetail errorDetail = new ErrorDetail(LocalDateTime.now(),
                exception.getMessage(),
                "DATA_NOT_FOUND",
                request.getDescription(false));
        LOGGER.error(exception.getMessage());
        return new ResponseEntity<>(errorDetail, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> globleExcpetionHandler(Exception exception, WebRequest request) {
        ErrorDetail errorDetails = new ErrorDetail(LocalDateTime.now(),
                exception.getMessage(),
                "INTERNAL_SERVER_ERROR",
                request.getDescription(false));
        LOGGER.error(exception.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
