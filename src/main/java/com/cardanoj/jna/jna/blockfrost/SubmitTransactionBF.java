package com.cardanoj.jna.jna.blockfrost;

import com.cardanoj.jna.backend.api.BackendService;
import com.cardanoj.jna.blockfrost.service.BFBackendService;
import com.cardanoj.jna.coreapi.model.Result;
import com.cardanoj.jna.jna.common.ConstantsApi;
import com.cardanoj.jna.jna.main.JNAUtil;
import com.cardanoj.jna.util.HexUtil;

import java.math.BigInteger;

import static com.cardanoj.jna.jna.common.ConstantsApi.BF_API_KEY;

public class SubmitTransactionBF {

    public void submitTransaction(String senderAddress, String receiverAddress, BigInteger amount, String message) throws Exception {

        String signedTxnHex = SignTransactionBF.signTransaction(senderAddress, receiverAddress, amount, message);
        System.out.println("Signed Transaction Hex: " + signedTxnHex);

        boolean isValidSignedHex = JNAUtil.validateTransactionCBOR(signedTxnHex);
        if(isValidSignedHex) {
            System.out.println("Valid Signed CBOR!");
        } else {
            System.out.println("Invalid Signed CBOR!");
        }

        byte[] signedTxnBytes = HexUtil.decodeHexString(signedTxnHex);

        BackendService backendService = new BFBackendService(ConstantsApi.BF_PREVIEW_URL, BF_API_KEY);

        // Submit
        Result<String> txnId = backendService.getTransactionService().submitTransaction(signedTxnBytes);
        if (txnId.isSuccessful()) {
            System.out.println();
            System.out.println("------- Transaction submitted successfully! -------");
            System.out.println("Transaction ID: " + txnId.getValue());
            System.out.println("https://preview.cardanoscan.io/transaction/" + txnId.getValue());
        } else {
            System.out.println("Transaction submission failed: " + txnId);
        }
    }

}
