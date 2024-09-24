package com.cardanoj.jna.coreapi.helper;

import com.cardanoj.jna.coreapi.exception.ApiException;
import com.cardanoj.jna.coreapi.model.ProtocolParams;
import com.cardanoj.jna.coreapi.model.Utxo;
import com.cardanoj.jna.coinselection.UtxoSelectionStrategy;
import com.cardanoj.jna.exception.AddressExcepion;
import com.cardanoj.jna.metadata.Metadata;
import com.cardanoj.jna.coreapi.transaction.model.MintTransaction;
import com.cardanoj.jna.coreapi.transaction.model.PaymentTransaction;
import com.cardanoj.jna.coreapi.transaction.model.TransactionDetailsParams;
import com.cardanoj.jna.transaction.spec.Transaction;

import java.math.BigInteger;
import java.util.List;

/**
 * Interface to build transaction from higher level transaction request apis.
 * It uses {@link UtxoSelectionStrategy} to get appropriate Utxos
 */
public interface UtxoTransactionBuilder {
    /**
     * Set utxo selection strategy
     * @param utxoSelectionStrategy
     */
    void setUtxoSelectionStrategy(UtxoSelectionStrategy utxoSelectionStrategy);

    /**
     * Build Transaction for list of Payment Transactions
     * @param transactions
     * @param detailsParams
     * @param metadata
     * @return
     * @throws ApiException
     * @throws AddressExcepion
     */
    Transaction buildTransaction(List<PaymentTransaction> transactions, TransactionDetailsParams detailsParams, Metadata metadata, ProtocolParams protocolParams) throws ApiException,
            AddressExcepion;

    /**
     * Get required utxos by address, unit and amount by calling {@link UtxoSelectionStrategy}
     * @param address
     * @param unit
     * @param amount
     * @return
     * @throws ApiException
     * @deprecated use {@link UtxoSelectionStrategy} directly
     */
    @Deprecated
    List<Utxo> getUtxos(String address, String unit, BigInteger amount) throws ApiException;

    /**
     * Build Transaction for token minting
     * @param mintTransaction
     * @param detailsParams
     * @param metadata
     * @return
     * @throws ApiException
     * @throws AddressExcepion
     */
    Transaction buildMintTokenTransaction(MintTransaction mintTransaction, TransactionDetailsParams detailsParams, Metadata metadata, ProtocolParams protocolParams) throws ApiException, AddressExcepion;
}
