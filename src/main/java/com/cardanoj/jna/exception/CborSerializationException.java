package com.cardanoj.jna.exception;

public class CborSerializationException extends Exception {
    public CborSerializationException(String message) {
        super(message);
    }

    public CborSerializationException(String message, Exception e) {
        super(message, e);
    }
}
