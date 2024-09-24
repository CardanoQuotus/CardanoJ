package com.cardanoj.jna.jna.blockfrost;

import com.cardanoj.jna.crypto.cip20.MessageMetadata;
import com.cardanoj.jna.transaction.spec.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import static com.cardanoj.jna.jna.blockfrost.utils.CreateMetadata.createMetadata;
import static com.cardanoj.jna.jna.blockfrost.utils.FeeEstimation.estimateFee;
import static com.cardanoj.jna.jna.blockfrost.utils.FetchCurrentSlot.fetchCurrentSlot;
import static com.cardanoj.jna.jna.blockfrost.utils.FetchProtocolParameters.fetchProtocolParameters;
import static com.cardanoj.jna.jna.blockfrost.utils.FetchUTXOs.fetchUTXOs;

public class RawTransactionHexBF {

    public static String genRawTransactionHex(String senderAddress, String receiverAddress, BigInteger amountToSend, String message) throws Exception {

        int currentSlotNumber = fetchCurrentSlot();
        int ttl = currentSlotNumber + 10000;

        // Fetch UTXOs for the sender's address
        JSONArray utxos = fetchUTXOs(senderAddress);
        if (utxos.length() == 0) {
            throw new RuntimeException("No UTXOs found for address: " + senderAddress);
        }

        // Fetch protocol parameters
        JSONObject protocolParameters = fetchProtocolParameters();

        BigInteger totalInputValue = BigInteger.ZERO;
        List<TransactionInput> inputList = new ArrayList<>();
        for (int i = 0; i < utxos.length(); i++) {
            JSONObject utxo = utxos.getJSONObject(i);
            String txHash = utxo.getString("tx_hash");
            int txIndex = utxo.getInt("tx_index");
            BigInteger amount = new BigInteger(utxo.getJSONArray("amount").getJSONObject(0).getString("quantity"));
            totalInputValue = totalInputValue.add(amount);

            TransactionInput txnInput = new TransactionInput();
            txnInput.setTransactionId(txHash);
            txnInput.setIndex(txIndex);
            inputList.add(txnInput);

            // Stop if we have enough input value
            if (totalInputValue.compareTo(amountToSend) >= 0) {
                break;
            }
        }

        // Estimate the transaction size
        int txSize = 200 + inputList.size() * 100; // Rough estimate, adjust as needed

        // Estimate fee dynamically
        BigInteger estimatedFee = estimateFee(txSize, protocolParameters);

        // Ensure we have enough input value
        if (totalInputValue.compareTo(amountToSend.add(estimatedFee)) < 0) {
            throw new RuntimeException("Not enough funds in the UTXOs.");
        }

        // Change value
        BigInteger changeValue = totalInputValue.subtract(amountToSend).subtract(estimatedFee);

        TransactionBody txnBody = new TransactionBody();
        txnBody.setInputs(inputList);

        // Adding transaction outputs
        TransactionOutput txnOutput = new TransactionOutput();
        txnOutput.setAddress(receiverAddress); // receiver
        txnOutput.setValue(new Value(amountToSend, null));

        TransactionOutput changeOutput = new TransactionOutput();
        changeOutput.setAddress(senderAddress); // sender
        changeOutput.setValue(new Value(changeValue, null));

        List<TransactionOutput> outputs = new ArrayList<>();
        outputs.add(txnOutput);
        outputs.add(changeOutput);
        txnBody.setOutputs(outputs);

        txnBody.setFee(estimatedFee);
        txnBody.setTtl(ttl);

        MessageMetadata metadata = createMetadata(message);
        AuxiliaryData auxiliaryData = new AuxiliaryData();
        auxiliaryData.setMetadata(metadata);

        // Serializing the transaction
        Transaction transaction = new Transaction();
        transaction.setBody(txnBody);
        transaction.setAuxiliaryData(auxiliaryData);

        String initialHexStr = transaction.serializeToHex();
        int finalTxSize = initialHexStr.length() / 2;

        BigInteger finalEstimatedFee = estimateFee(finalTxSize, protocolParameters);

        BigInteger feeBuffer = BigInteger.valueOf(10000);
        finalEstimatedFee = finalEstimatedFee.add(feeBuffer);

        if(totalInputValue.compareTo(amountToSend.add(finalEstimatedFee)) < 0) {
            throw new RuntimeException("Not enough funds in the UTXOs after re-estimating the fee.");
        }

        BigInteger finalChangeValue = totalInputValue.subtract(amountToSend).subtract(finalEstimatedFee);

        // Update transaction outputs with final change value and fee
        changeOutput.setValue(new Value(finalChangeValue, null));
        txnBody.setFee(finalEstimatedFee);

        String finalHexStr = transaction.serializeToHex();

        return finalHexStr;
    }
}
