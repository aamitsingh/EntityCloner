package com.workspan.entity.cloner.exception;

public class EntityFormatException extends Exception {
    public EntityFormatException(String message) {
        super(message);
    }

    public EntityFormatException(String message, Throwable cause) {
        super(message, cause);
    }
}
