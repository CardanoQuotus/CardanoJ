package com.cardanoj.jna.coreapi;

import com.cardanoj.jna.coreapi.exception.ApiException;
import com.cardanoj.jna.coreapi.model.Result;

/**
 * Implement this interface to provide transaction submission capability.
 */
public interface TransactionProcessor extends TransactionEvaluator {

    Result<String> submitTransaction(byte[] cborData) throws ApiException;
}
