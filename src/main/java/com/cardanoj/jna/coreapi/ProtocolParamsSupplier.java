package com.cardanoj.jna.coreapi;

import com.cardanoj.jna.coreapi.model.ProtocolParams;

/**
 * Implement this interface to provide ProtocolParams
 */
@FunctionalInterface
public interface ProtocolParamsSupplier {
    ProtocolParams getProtocolParams();
}
