package org.example.util.exceptions.custom;

import org.example.util.exceptions.ServiceException;

public class BookException extends ServiceException {
    public BookException() {
    }

    public BookException(String message) {
        super(message);
    }

    public BookException(String message, Throwable cause) {
        super(message, cause);
    }
}
