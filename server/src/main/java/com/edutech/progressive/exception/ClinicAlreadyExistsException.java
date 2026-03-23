package com.edutech.progressive.exception;

public class ClinicAlreadyExistsException extends RuntimeException {
    public ClinicAlreadyExistsException(String message) {
        super(message);
    }
}