package com.cardanoj.jna.function.helper;

import com.cardanoj.jna.coreapi.model.Utxo;
import com.cardanoj.jna.plutus.spec.Redeemer;
import com.cardanoj.jna.transaction.spec.Transaction;
import com.cardanoj.jna.transaction.spec.TransactionInput;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class RedeemerUtil {

    public static TransactionInput getScriptInputFromRedeemer(Redeemer redeemer, Transaction transaction) {
        List<TransactionInput> sortedInputs = getSortedInputs(transaction);
        int index = redeemer.getIndex().intValue();
        return sortedInputs.get(index);
    }

    public static int getScriptInputIndex(Utxo utxo, Transaction transaction) {
        List<TransactionInput> sortedInputs = getSortedInputs(transaction);
        int index = sortedInputs.indexOf(new TransactionInput(utxo.getTxHash(), utxo.getOutputIndex()));
        return index;
    }

    public static int getScriptInputIndex(TransactionInput input, Transaction transaction) {
        List<TransactionInput> sortedInputs = getSortedInputs(transaction);
        int index = sortedInputs.indexOf(input);
        return index;
    }

    private static List<TransactionInput> getSortedInputs(Transaction transaction) {
        List<TransactionInput> copyInputs = transaction.getBody().getInputs()
                .stream()
                .collect(Collectors.toList());
        copyInputs.sort(
                Comparator.comparing(TransactionInput::getTransactionId)
                        .thenComparing(TransactionInput::getIndex)
        );
        return copyInputs;
    }
}
