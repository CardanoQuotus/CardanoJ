package com.cardanoj.jna.jna.cliApi;

import com.cardanoj.jna.jna.cliApi.api.SignTransactionService;
import com.google.gson.JsonObject;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;

import static com.cardanoj.jna.jna.common.ConstantsApi.CLI_BASE_URL;


public class SignTransactionServiceImpl {
    private SignTransactionService signTransactionService;

    public SignTransactionServiceImpl() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(CLI_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        signTransactionService = retrofit.create(SignTransactionService.class);

    }

    public JsonObject signTransaction(String signKey, String txBody) {
        Call<JsonObject> call = signTransactionService.signTransaction(signKey, txBody);
        try {
            Response<JsonObject> response = call.execute();
            if (response.isSuccessful()) {
                return response.body();
            } else {
                System.err.println("Request failed: " + response.code() + " " + response.message());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}