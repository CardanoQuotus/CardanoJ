package com.cardanoj.jna.jna.cliApi.api;

import com.cardanoj.jna.jna.cliApi.common.Utxo;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

import java.util.List;

public interface QueryUTXOService {
    @GET("/api/queryutxo/{address}")
    Call<List<Utxo>> getUtxoList(@Path("address") String address);
}
