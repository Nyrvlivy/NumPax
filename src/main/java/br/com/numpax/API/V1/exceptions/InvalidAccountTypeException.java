package br.com.numpax.API.V1.exceptions;

public class InvalidAccountTypeException extends RuntimeException {
    public InvalidAccountTypeException(String message) {
        super(message);
    }

    public InvalidAccountTypeException(String message, Throwable cause) {
        super(message, cause);
    }
}