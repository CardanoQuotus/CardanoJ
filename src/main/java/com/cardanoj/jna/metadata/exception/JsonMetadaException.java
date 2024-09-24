package com.cardanoj.jna.metadata.exception;

public class JsonMetadaException extends RuntimeException {
    public JsonMetadaException(String messsage) {
        super(messsage);
    }

    public JsonMetadaException(String message, Exception e) {
        super(message, e);
    }
}
