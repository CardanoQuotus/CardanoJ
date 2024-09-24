package com.cardanoj.jna.blockfrost.service;

import com.cardanoj.jna.coreapi.common.OrderEnum;
import com.cardanoj.jna.coreapi.exception.ApiException;
import com.cardanoj.jna.coreapi.model.Result;
import com.cardanoj.jna.coreapi.model.Utxo;
import com.cardanoj.jna.backend.api.TransactionService;
import com.cardanoj.jna.backend.api.UtxoService;
import com.cardanoj.jna.blockfrost.service.http.AddressesApi;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.util.List;

public class BFUtxoService extends BFBaseService implements UtxoService {

    private final AddressesApi addressApi;
    private final TransactionService transactionService;

    public BFUtxoService(String baseUrl, String projectId) {
        super(baseUrl, projectId);
        this.addressApi = getRetrofit().create(AddressesApi.class);
        this.transactionService = new BFTransactionService(baseUrl, projectId);
    }

    @Override
    public Result<List<Utxo>> getUtxos(String address, int count, int page) throws ApiException {
        return getUtxos(address, count, page, OrderEnum.asc);
    }

    public Result<List<Utxo>> getUtxos(String address, int count, int page, OrderEnum order) throws ApiException {
        Call<List<Utxo>> utxosCall = addressApi.getUtxos(getProjectId(), address, count, page, order.toString());

        try {
            Response<List<Utxo>> response = utxosCall.execute();
            return processResponse(response);

        } catch (IOException e) {
            throw new ApiException("Error getting utxos", e);
        }
    }

    @Override
    public Result<List<Utxo>> getUtxos(String address, String unit, int count, int page) throws ApiException {
        return getUtxos(address, unit, count, page, OrderEnum.asc);
    }

    @Override
    public Result<List<Utxo>> getUtxos(String address, String unit, int count, int page, OrderEnum order) throws ApiException {
        Call<List<Utxo>> utxosCall = addressApi.getUtxosByAsset(getProjectId(), address, unit, count, page, order.toString());

        try {
            Response<List<Utxo>> response = utxosCall.execute();
            return processResponse(response);
        } catch (IOException e) {
            throw new ApiException("Error getting utxos for address : " + address + ", asset: " + unit, e);
        }
    }

    @Override
    public Result<Utxo> getTxOutput(String txHash, int outputIndex) throws ApiException {
        return transactionService.getTransactionOutput(txHash, outputIndex);
    }
}
