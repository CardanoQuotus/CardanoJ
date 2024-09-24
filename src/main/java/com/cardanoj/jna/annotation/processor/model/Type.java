package com.cardanoj.jna.annotation.processor.model;

public enum Type {
    INTEGER("integer"),
    STRING("string"),
    BYTES("bytes"),
    LIST("list"),
    MAP("map"),
    BOOL("bool"),
    CONSTRUCTOR("constructor"),
    OPTIONAL("optional");

    private String type;
    Type(String type) {
        this.type = type;
    }
}
