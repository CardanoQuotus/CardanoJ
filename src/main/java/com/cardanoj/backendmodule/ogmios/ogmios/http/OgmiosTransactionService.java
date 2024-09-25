package com.cardanoj.backendmodule.ogmios.ogmios.http;

import com.cardanoj.coreapi.exception.ApiException;
import com.cardanoj.coreapi.model.EvaluationResult;
import com.cardanoj.coreapi.model.Result;
import com.cardanoj.coreapi.model.Utxo;
import com.cardanoj.backend.api.TransactionService;
import com.cardanoj.backend.model.TransactionContent;
import com.cardanoj.backend.model.TxContentRedeemers;
import com.cardanoj.backend.model.TxContentUtxo;

import com.cardanoj.supplier.ogmios.OgmiosTransactionProcessor;

import java.util.List;

public class OgmiosTransactionService implements TransactionService {

    private final OgmiosTransactionProcessor ogmiosTransactionProcessor;

    public OgmiosTransactionService(String baseUrl) {
        this.ogmiosTransactionProcessor = new OgmiosTransactionProcessor(baseUrl);
    }

    @Override
    public Result<String> submitTransaction(byte[] cborData) throws ApiException {
        return ogmiosTransactionProcessor.submitTransaction(cborData);
    }

    @Override
    public Result<TransactionContent> getTransaction(String txnHash) throws ApiException {
        throw new UnsupportedOperationException("Not supported yet");
    }

    @Override
    public Result<List<TransactionContent>> getTransactions(List<String> txnHashCollection) throws ApiException {
        throw new UnsupportedOperationException("Not supported yet");
    }

    @Override
    public Result<TxContentUtxo> getTransactionUtxos(String txnHash) throws ApiException {
        throw new UnsupportedOperationException("Not supported yet");
    }

    @Override
    public Result<List<TxContentRedeemers>> getTransactionRedeemers(String txnHash) throws ApiException {
        throw new UnsupportedOperationException("Not supported yet");
    }

    @Override
    public Result<Utxo> getTransactionOutput(String txnHash, int outputIndex) throws ApiException {
        throw new UnsupportedOperationException("Not supported yet");
    }

    @Override
    public Result<List<EvaluationResult>> evaluateTx(byte[] cborData) throws ApiException {
        return ogmiosTransactionProcessor.evaluateTx(cborData);
    }


}
