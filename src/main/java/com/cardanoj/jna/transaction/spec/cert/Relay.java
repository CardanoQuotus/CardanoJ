package com.cardanoj.jna.transaction.spec.cert;

import co.nstant.in.cbor.model.Array;
import com.cardanoj.jna.exception.CborSerializationException;

public interface Relay {
    Array serialize() throws CborSerializationException;
}
