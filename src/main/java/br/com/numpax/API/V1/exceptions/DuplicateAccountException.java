package br.com.numpax.API.V1.exceptions;

public class DuplicateAccountException extends RuntimeException {
    
    public DuplicateAccountException(String message) {
        super(message);
    }
    
    public DuplicateAccountException(String message, Throwable cause) {
        super(message, cause);
    }
} 