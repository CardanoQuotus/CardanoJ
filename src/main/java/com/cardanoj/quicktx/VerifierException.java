package com.cardanoj.quicktx;

/**
 * Thrown when verification fails
 */
public class VerifierException extends RuntimeException {
    public VerifierException(String errorMessage) {
        super(errorMessage);
    }
}
