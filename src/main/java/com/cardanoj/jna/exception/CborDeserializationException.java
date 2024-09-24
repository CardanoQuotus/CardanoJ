package com.cardanoj.jna.exception;

public class CborDeserializationException extends Exception {
    public CborDeserializationException(String message) {
        super(message);
    }

    public CborDeserializationException(String message, Exception e) {
        super(message, e);
    }
}
