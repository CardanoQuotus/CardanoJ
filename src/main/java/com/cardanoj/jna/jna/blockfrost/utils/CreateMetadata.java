package com.cardanoj.jna.jna.blockfrost.utils;

import com.cardanoj.jna.crypto.cip20.MessageMetadata;

public class CreateMetadata {
    public static MessageMetadata createMetadata(String message) {
        MessageMetadata metadata = MessageMetadata.create();
        metadata.add(message);
        return metadata;
    }
}
