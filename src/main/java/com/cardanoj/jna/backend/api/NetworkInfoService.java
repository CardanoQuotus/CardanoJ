package com.cardanoj.jna.backend.api;

import com.cardanoj.jna.coreapi.exception.ApiException;
import com.cardanoj.jna.coreapi.model.Result;
import com.cardanoj.jna.backend.model.Genesis;

public interface NetworkInfoService {
    /**
     *
     * @return Genesis Info
     * @throws ApiException
     */
    Result<Genesis> getNetworkInfo() throws ApiException;
}
