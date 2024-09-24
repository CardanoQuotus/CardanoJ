package com.cardanoj.jna.jna.blockfrost;

import com.cardanoj.jna.jna.main.JNAUtil;

import java.math.BigInteger;

public class SignTransactionBF {

    public static String signTransaction(String senderAddress, String receiverAddress, BigInteger amount, String message) throws Exception {
        System.out.println();
        // Sign
        String hexStr = RawTransactionHexBF.genRawTransactionHex(senderAddress, receiverAddress, amount, message);
        System.out.println("Raw Transaction Hex: " + hexStr);

        boolean isValidHexStr = JNAUtil.validateTransactionCBOR(hexStr);
        if(isValidHexStr) {
            System.out.println("Valid Raw Transaction CBOR!");
        } else {
            System.out.println("Invalid Raw Transaction CBOR!");
        }

        String mnemonic = "brother verify rabbit join misery retire mother surprise game ignore main chase crowd captain miracle coast inflict half margin bid toy stairs right plastic";

        String bech32PrivateKey = JNAUtil.getPrivateKeyFromMnemonic(mnemonic,0);
//        System.out.println("bech32PrivateKey: " + bech32PrivateKey);

        String signedTxnHex = JNAUtil.sign(hexStr, bech32PrivateKey);
        return signedTxnHex;
    }
}
