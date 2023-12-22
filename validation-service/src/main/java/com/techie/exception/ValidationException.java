package com.techie.exception;

import org.springframework.http.HttpStatus;

public class ValidationException extends RuntimeException {
    private String errorCode;
    private String errorMessage;
    private HttpStatus httpStatus;

    public ValidationException() {

    }

    public ValidationException(String errorCode, String errorMessage, HttpStatus httpStatus) {
        super();
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.httpStatus = httpStatus;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }
}
