package com.cardanoj.supplier.ogmios;

import com.cardanoj.coreapi.TransactionEvaluator;
import com.cardanoj.coreapi.exception.ApiException;
import com.cardanoj.coreapi.model.EvaluationResult;
import com.cardanoj.coreapi.model.Result;
import com.cardanoj.coreapi.model.Utxo;
import com.cardanoj.transaction.spec.Transaction;

import java.util.List;
import java.util.Set;

public class OgmiosTransactionEvaluator implements TransactionEvaluator {
    private final OgmiosTransactionProcessor ogmiosTransactionProcessor;

    public OgmiosTransactionEvaluator(String baseUrl) {
        this.ogmiosTransactionProcessor = new OgmiosTransactionProcessor(baseUrl);
    }

    @Override
    public Result<List<EvaluationResult>> evaluateTx(byte[] cbor, Set<Utxo> inputUtxos) throws ApiException {
        return ogmiosTransactionProcessor.evaluateTx(cbor, inputUtxos);
    }

    @Override
    public Result<List<EvaluationResult>> evaluateTx(byte[] cbor) throws ApiException {
        return ogmiosTransactionProcessor.evaluateTx(cbor);
    }

    @Override
    public Result<List<EvaluationResult>> evaluateTx(Transaction transaction, Set<Utxo> inputUtxos) throws ApiException {
        return ogmiosTransactionProcessor.evaluateTx(transaction, inputUtxos);
    }
}
