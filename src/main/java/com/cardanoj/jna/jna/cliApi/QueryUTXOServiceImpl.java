package com.cardanoj.jna.jna.cliApi;

import com.cardanoj.jna.jna.cliApi.api.QueryUTXOService;
import com.cardanoj.jna.jna.cliApi.common.Utxo;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.List;

import static com.cardanoj.jna.jna.common.ConstantsApi.CLI_BASE_URL;

public class QueryUTXOServiceImpl {
    private String txHash = "";
    private String txId = "";
    private String amount = "";

    public QueryUTXOServiceImpl(String address, int position) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(CLI_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        QueryUTXOService queryUTXOService = retrofit.create(QueryUTXOService.class);

        Call<List<Utxo>> call = queryUTXOService.getUtxoList(address);
        try {
            Response<List<Utxo>> response = call.execute();
            if (response.isSuccessful()) {
                List<Utxo> utxos = response.body();
                txHash = utxos.get(position).getTxHash();
                txId = utxos.get(position).getTxIx();
                amount = utxos.get(position).getAmount();

//                Gson gson = new Gson();
//                String jsonOutput = gson.toJson(utxos);
//                System.out.println("Query response: " + jsonOutput);
            } else {
                System.err.println("Request failed: " + response.code() + " " + response.message());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getTxHash() {
        return txHash;
    }

    public String getTxId() {
        return txId;
    }

    public String getAmount() {
        return amount;
    }
}
