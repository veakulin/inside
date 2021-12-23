package ru.akulin.exceptions;

public class MessageControllerException extends RuntimeException {

    public MessageControllerException() {
    }

    public MessageControllerException(String message) {
        super(message);
    }

    public MessageControllerException(String message, Throwable cause) {
        super(message, cause);
    }

    public MessageControllerException(Throwable cause) {
        super(cause);
    }

    public MessageControllerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
