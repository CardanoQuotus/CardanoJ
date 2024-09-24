package com.cardanoj.jna.jna.CLI;

import com.cardanoj.jna.jna.cliApi.SubmitTransactionServiceImpl;
import com.google.gson.JsonObject;

public class SubmitTransactionCLI {

    public static void submitTransaction(String senderAddress, String receiverAddress, String amount) throws Exception {

        String signedTransactionHexCLI = SignTransactionCLI.signCLI(senderAddress, receiverAddress, amount);
//        System.out.println("Signed Transaction Hex : " + signedTransactionHexCLI);

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("type", "Witnessed Tx BabbageEra");
        jsonObject.addProperty("description", "Ledger Cddl Format");
        jsonObject.addProperty("cborHex", signedTransactionHexCLI);

//        System.out.println("JSON to be encoded: " + jsonObject.toString());

        // Submit
        SubmitTransactionServiceImpl submitTransactionService = new SubmitTransactionServiceImpl();
        String txId = String.valueOf(submitTransactionService.submitTransaction(jsonObject.toString()));

        System.out.println();
        System.out.println("------- Transaction submitted successfully! -------");
        System.out.println("Transaction Id: " + txId);
    }
}
