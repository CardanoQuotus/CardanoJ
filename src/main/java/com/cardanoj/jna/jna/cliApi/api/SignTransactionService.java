package com.cardanoj.jna.jna.cliApi.api;

import com.google.gson.JsonObject;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SignTransactionService {
    @GET("/api/sign")
    Call<JsonObject> signTransaction(
            @Query("signKey") String signKey,
            @Query("txbody") String txBody
    );
}
