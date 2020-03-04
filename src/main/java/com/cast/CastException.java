package com.cast;

public class CastException extends RuntimeException {

    public CastException(Class<?> sourceClass, Class<?> targetClass) {
        super(sourceClass + " cannot cast to " + targetClass);
    }

    public CastException(Class<?> sourceClass, Class<?> targetClass, Throwable cause) {
        super(sourceClass + " cannot cast to " + targetClass, cause);
    }

    public CastException(String message) {
        super(message);
    }

    public CastException(String message, Throwable cause) {
        super(message, cause);
    }

    public CastException(Throwable cause) {
        super(cause);
    }
}
