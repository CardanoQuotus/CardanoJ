package com.cardanoj.jna.backend.api;

import com.cardanoj.jna.coreapi.TransactionProcessor;
import com.cardanoj.jna.coreapi.exception.ApiException;
import com.cardanoj.jna.coreapi.model.EvaluationResult;
import com.cardanoj.jna.coreapi.model.Result;
import com.cardanoj.jna.coreapi.model.Utxo;

import java.util.List;
import java.util.Set;

/**
 * Default implementation of TransactionProcessor which uses Backend service's TransactionService
 */
public class DefaultTransactionProcessor implements TransactionProcessor {
    private final TransactionService transactionService;

    public DefaultTransactionProcessor(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @Override
    public Result<String> submitTransaction(byte[] cborData) throws ApiException {
        return transactionService.submitTransaction(cborData);
    }

    @Override
    public Result<List<EvaluationResult>> evaluateTx(byte[] cborData, Set<Utxo> inputUtxos) throws ApiException {
        return transactionService.evaluateTx(cborData);
    }
}
