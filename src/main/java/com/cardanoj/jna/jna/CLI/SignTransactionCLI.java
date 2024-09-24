package com.cardanoj.jna.jna.CLI;

import com.cardanoj.jna.exception.CborSerializationException;
import com.cardanoj.jna.jna.main.JNAUtil;

import java.io.UnsupportedEncodingException;
import java.util.Scanner;

public class SignTransactionCLI {
    public static String signCLI(String senderAddress, String receiverAddress, String amount) throws CborSerializationException, UnsupportedEncodingException {
        Scanner sc = new Scanner(System.in);

        // Fetch the rawTxnHex value from RawTxnHex class
        String rawTxnInHexCLI = RawTransactionHexCLI.rawHexGenCLI(senderAddress, receiverAddress, amount);
        System.out.println("Raw Transaction Hex CLI: " + rawTxnInHexCLI);

        boolean validRawTxn = JNAUtil.validateTransactionCBOR(rawTxnInHexCLI);
        if (validRawTxn) {
            System.out.println("Valid Raw Transaction CBOR!");
        } else {
            System.out.println("Invalid Raw Transaction CBOR!");
        }

        String mnemonic = "brother verify rabbit join misery retire mother surprise game ignore main chase crowd captain miracle coast inflict half margin bid toy stairs right plastic";

        String bech32PrivateKey = JNAUtil.getPrivateKeyFromMnemonic(mnemonic,0);
        System.out.println("bech32PrivateKey: " + bech32PrivateKey);

        // Sign
        String signedTxnHexCLI = JNAUtil.sign(rawTxnInHexCLI, bech32PrivateKey);
        System.out.println("Signed Transaction Hex: " + signedTxnHexCLI); // this is required for transaction

        boolean validSignTxn = JNAUtil.validateTransactionCBOR(rawTxnInHexCLI);
        if (validSignTxn) {
            System.out.println("Valid Sign Transaction CBOR!");
        } else {
            System.out.println("Invalid Sign Transaction CBOR!");
        }

        sc.close();
        return signedTxnHexCLI;
    }
}
