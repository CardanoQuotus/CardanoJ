package com.cardanoj.jna.jna.CLI;

import com.cardanoj.jna.jna.cliApi.BuildTransactionServiceImpl;
import com.cardanoj.jna.jna.cliApi.QueryUTXOServiceImpl;

import java.io.UnsupportedEncodingException;

public class RawTransactionHexCLI {

    public static String rawHexGenCLI(String senderAddress, String receiverAddress, String amount) throws UnsupportedEncodingException {
//        String SenderAddress = "addr_test1qre2prk90hf2k6uxjjtdwzsylnhx7ace68ufwrld5498w2l22txy0szz4y7unrmcjwq8usq08sym7ktcmdqxf6727ykq8ftyh7";
//        String ReceiverAddress = "addr_test1qre2prk90hf2k6uxjjtdwzsylnhx7ace68ufwrld5498w2l22txy0szz4y7unrmcjwq8usq08sym7ktcmdqxf6727ykq8ftyh7";
//        String amount = "";

        String txHashId = "";
        String cborHex = "";

        QueryUTXOServiceImpl queryUTXOService = new QueryUTXOServiceImpl(senderAddress, 0);
        txHashId = queryUTXOService.getTxHash() + "#" + queryUTXOService.getTxId();
        System.out.println("txHashId: " + txHashId);

        BuildTransactionServiceImpl buildTransaction = new BuildTransactionServiceImpl();
        String txBuild = String.valueOf(buildTransaction.build(senderAddress, receiverAddress, amount, queryUTXOService.getTxHash(), queryUTXOService.getTxId()));
        System.out.println("txBuild: " + txBuild);

        cborHex = buildTransaction.getCborHex();
//        System.out.println("CborHex: " + cborHex);

        return cborHex;

    }
}
