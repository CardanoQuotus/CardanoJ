package com.cardanoj.jna.jna.cliApi.api;

import com.google.gson.JsonObject;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SubmitTransactionService {
    @GET("/api/submit")
    Call<JsonObject> submitTransaction(
            @Query("tx") String tx
    );
}
