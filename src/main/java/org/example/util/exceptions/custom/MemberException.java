package org.example.util.exceptions.custom;

import org.example.util.exceptions.ServiceException;

public class MemberException extends ServiceException {
    public MemberException() {
    }

    public MemberException(String message) {
        super(message);
    }

    public MemberException(String message, Throwable cause) {
        super(message, cause);
    }

}
