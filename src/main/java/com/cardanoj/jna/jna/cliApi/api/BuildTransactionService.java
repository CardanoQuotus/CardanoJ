package com.cardanoj.jna.jna.cliApi.api;

import com.google.gson.JsonObject;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface BuildTransactionService {
    @GET("/api/build/{sender}/{receiver}/{lovelace}/{txHash}/{txId}")
    Call<JsonObject> getTransaction(
            @Path("sender") String sender,
            @Path("receiver") String receiver,
            @Path("lovelace") String lovelace,
            @Path("txHash") String txHash,
            @Path("txId") String txId
    );
}
