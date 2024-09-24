package com.cardanoj;

import com.cardanoj.backend.api.BackendService;
import com.cardanoj.backend.api.DefaultProtocolParamsSupplier;
import com.cardanoj.backend.api.DefaultUtxoSupplier;
import com.cardanoj.backendmodule.koios.main.KoiosBackendService;
import com.cardanoj.common.model.Networks;
import com.cardanoj.coreapi.ProtocolParamsSupplier;
import com.cardanoj.coreapi.UtxoSupplier;
import com.cardanoj.coreapi.account.Account;
import com.cardanoj.coreapi.model.Result;
import com.cardanoj.crypto.cip20.MessageMetadata;
import com.cardanoj.function.Output;
import com.cardanoj.function.TxBuilder;
import com.cardanoj.function.TxBuilderContext;
import com.cardanoj.transaction.spec.Transaction;

import java.util.Scanner;

import static com.cardanoj.backendmodule.koios.main.Constants.KOIOS_PREVIEW_URL;
import static com.cardanoj.common.ADAConversionUtil.adaToLovelace;
import static com.cardanoj.common.CardanoConstants.LOVELACE;
import static com.cardanoj.function.helper.AuxDataProviders.metadataProvider;
import static com.cardanoj.function.helper.BalanceTxBuilders.balanceTx;
import static com.cardanoj.function.helper.InputBuilders.createFromSender;
import static com.cardanoj.function.helper.SignerProviders.signerFrom;

public class SimpleTransfer {

    public void transfer() throws Exception {
        Scanner sc = new Scanner(System.in) ;

//        Sender address : addr_test1qrnzwcuxuheze4x2raqfjyncxxrux794wgz2c8lex2my50mxw56jfn7nl88j5dfz3f42y02hahss973geqrugesq0m7qe8ft2g

        //Sender account
        String senderMnemonic;
        System.out.println("Enter Mnemonic : ");
        senderMnemonic = sc.nextLine();
        Account senderAccount = new Account(Networks.preview(), senderMnemonic);
        String senderAddress = senderAccount.baseAddress();
        System.out.println("Addrress : " + senderAddress + "\n");


//        System.out.println("Enter receiver 1's address : ");
//        String receiverAddress1 = sc.nextLine();
//        System.out.println("Enter receiver 2's address : ");
//        String receiverAddress2 = sc.nextLine();

        //Addresses to receive ada
        String receiverAddress1 = "addr_test1qqv5auuws3t8x2qjccc3u5g0tfcvwjnjvantxs83k6p8kmvjzgrfuhh95jay0p0he8kmxgsfh7s0m6jyjhnyw4azpwxs0ay5zm";
//        String receiverAddress2 = "addr_test1qre2prk90hf2k6uxjjtdwzsylnhx7ace68ufwrld5498w2l22txy0szz4y7unrmcjwq8usq08sym7ktcmdqxf6727ykq8ftyh7";

        // For Koios
        BackendService backendService = new KoiosBackendService(KOIOS_PREVIEW_URL);
        System.out.println("\n");

        // Define expected Outputs
        Output output1 = Output.builder()
                .address(receiverAddress1)
                .assetName(LOVELACE)
                .qty(adaToLovelace(10))
                .build();

//        Output output2 = Output.builder()
//                .address(receiverAddress2)
//                .assetName(LOVELACE)
//                .qty(adaToLovelace(20))
//                .build();

        // Create a CIP20 message metadata
        System.out.println("Enter the message for metadata : ");
        String cipMessage = sc.nextLine();

        MessageMetadata metadata = MessageMetadata.create()
                .add("Transaction : " + cipMessage + "\n");

//        MessageMetadata metadata = MessageMetadata.create()
//                .add("Transaction first Quotus");

        // Define TxBuilder
        TxBuilder txBuilder = output1.outputBuilder()
//                .and(output2.outputBuilder())
                .buildInputs(createFromSender(senderAddress, senderAddress))
                .andThen(metadataProvider(metadata))
                .andThen(balanceTx(senderAddress, 1));

        UtxoSupplier utxoSupplier = new DefaultUtxoSupplier(backendService.getUtxoService());
        ProtocolParamsSupplier protocolParamsSupplier = new DefaultProtocolParamsSupplier(backendService.getEpochService());

        //Build and sign the transaction
        Transaction signedTransaction = TxBuilderContext.init(utxoSupplier, protocolParamsSupplier)
                .buildAndSign(txBuilder, signerFrom(senderAccount));

        //Submit the transaction
        Result<String> result = backendService.getTransactionService().submitTransaction(signedTransaction.serialize());
        System.out.println(result);
        System.out.println("https://preview.cardanoscan.io/transaction/"+result.getValue());
    }

    public static void main(String[] args) throws Exception {
        new SimpleTransfer().transfer();
    }

}