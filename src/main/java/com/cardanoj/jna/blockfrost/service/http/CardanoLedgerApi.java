package com.cardanoj.jna.blockfrost.service.http;

import com.cardanoj.jna.backend.model.Genesis;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface CardanoLedgerApi {
    @GET("genesis")
    Call<Genesis> genesis(@Header("project_id") String projectId);
}
