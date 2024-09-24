package com.cardanoj.jna.exception;

public class AddressExcepion extends Exception {
    public AddressExcepion(String message) {
        super(message);
    }

    public AddressExcepion(String message, Exception e) {
        super(message, e);
    }
}
