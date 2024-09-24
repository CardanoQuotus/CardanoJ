package com.cardanoj.jna.jna.cliApi;

import com.cardanoj.jna.jna.cliApi.api.BuildTransactionService;
import com.google.gson.JsonObject;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;

import static com.cardanoj.jna.jna.common.ConstantsApi.CLI_BASE_URL;

public class BuildTransactionServiceImpl {
    private String type = "";
    private String description = "";
    private String cborHex = "";

    public JsonObject build(String senderAddress, String receiverAddress, String lovelace, String transactionHash, String transactionId) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(CLI_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        BuildTransactionService buildTransactionService = retrofit.create(BuildTransactionService.class);

        Call<JsonObject> call = buildTransactionService.getTransaction(senderAddress, receiverAddress, lovelace, transactionHash, transactionId);
        try {
            Response<JsonObject> response = call.execute();
            if (response.isSuccessful()) {
                JsonObject jsonResponse = response.body();
                if (jsonResponse != null) {
                    type = jsonResponse.get("type").getAsString();
                    description = jsonResponse.get("description").getAsString();
                    cborHex = jsonResponse.get("cborHex").getAsString();
                } else {
                    System.err.println("No build data received.");
                }
                return jsonResponse;
            } else {
                System.err.println("Request failed: " + response.code() + " " + response.message());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public String getCborHex() {
        return cborHex;
    }
}

