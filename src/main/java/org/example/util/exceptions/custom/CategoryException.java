package org.example.util.exceptions.custom;

import org.example.util.exceptions.ServiceException;

public class CategoryException extends ServiceException {
    public CategoryException() {
    }

    public CategoryException(String message) {
        super(message);
    }

    public CategoryException(String message, Throwable cause) {
        super(message, cause);
    }
}
