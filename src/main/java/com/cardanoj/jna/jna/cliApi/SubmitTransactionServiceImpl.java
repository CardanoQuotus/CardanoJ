package com.cardanoj.jna.jna.cliApi;

import com.cardanoj.jna.jna.cliApi.api.SubmitTransactionService;
import com.google.gson.JsonObject;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;

import static com.cardanoj.jna.jna.common.ConstantsApi.CLI_BASE_URL;

public class SubmitTransactionServiceImpl {
    private SubmitTransactionService submitTransactionService;

    public SubmitTransactionServiceImpl() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(CLI_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        submitTransactionService = retrofit.create(SubmitTransactionService.class);
    }

    public JsonObject submitTransaction(String signedTransaction) {
        Call<JsonObject> call = submitTransactionService.submitTransaction(signedTransaction);
        try {
            Response<JsonObject> response = call.execute();
            System.out.println("Submit : " + response.body());
            return response.body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}