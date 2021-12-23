package ru.akulin.exceptions;

public class LoginControllerException extends RuntimeException {

    public LoginControllerException() {
    }

    public LoginControllerException(String message) {
        super(message);
    }

    public LoginControllerException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoginControllerException(Throwable cause) {
        super(cause);
    }

    public LoginControllerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
