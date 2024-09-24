package com.cardanoj.jna.metadata.exception;

public class MetadataDeSerializationException extends RuntimeException {
    public MetadataDeSerializationException(String messsage) {
        super(messsage);
    }

    public MetadataDeSerializationException(String message, Exception e) {
        super(message, e);
    }
}
