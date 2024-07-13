package org.example.util.exceptions.custom;

import org.example.util.exceptions.ServiceException;

public class AuthorException extends ServiceException {
    public AuthorException() {
    }

    public AuthorException(String message) {
        super(message);
    }

    public AuthorException(String message, Throwable cause) {
        super(message, cause);
    }
}