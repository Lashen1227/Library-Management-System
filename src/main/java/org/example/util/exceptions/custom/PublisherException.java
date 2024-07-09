package org.example.util.exceptions.custom;

import org.example.util.exceptions.ServiceException;

public class PublisherException extends ServiceException {
    public PublisherException() {
    }

    public PublisherException(String message) {
        super(message);
    }

    public PublisherException(String message, Throwable cause) {
        super(message, cause);
    }
}