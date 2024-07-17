package org.example.util.exceptions.custom;

import org.example.util.exceptions.ServiceException;

public class AuthorExceptions extends ServiceException {
    public AuthorExceptions() {
    }

    public AuthorExceptions(String message) {
        super(message);
    }

    public AuthorExceptions(String message, Throwable cause) {
        super(message, cause);
    }
}