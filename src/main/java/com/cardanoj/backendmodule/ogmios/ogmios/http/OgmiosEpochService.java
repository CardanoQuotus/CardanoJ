package com.cardanoj.backendmodule.ogmios.ogmios.http;

import com.cardanoj.coreapi.exception.ApiException;
import com.cardanoj.coreapi.model.ProtocolParams;
import com.cardanoj.coreapi.model.Result;
import com.cardanoj.backend.api.EpochService;
import com.cardanoj.backend.model.EpochContent;
import com.cardanoj.supplier.ogmios.OgmiosProtocolParamSupplier;

public class OgmiosEpochService implements EpochService  {
    private final OgmiosProtocolParamSupplier ogmiosProtocolParamSupplier;

    public OgmiosEpochService(String baseUrl) {
        this.ogmiosProtocolParamSupplier = new OgmiosProtocolParamSupplier(baseUrl);
    }

    @Override
    public Result<EpochContent> getLatestEpoch() throws ApiException {
        throw new UnsupportedOperationException("Not supported yet");
    }

    @Override
    public Result<EpochContent> getEpoch(Integer epoch) throws ApiException {
        throw new UnsupportedOperationException("Not supported yet");
    }

    @Override
    public Result<ProtocolParams> getProtocolParameters(Integer epoch) throws ApiException {
        throw new UnsupportedOperationException("Not supported yet");
    }

    @Override
    public Result<ProtocolParams> getProtocolParameters() throws ApiException {
        ProtocolParams protocolParams = ogmiosProtocolParamSupplier.getProtocolParams();

        Result<ProtocolParams> result;
        if(protocolParams != null) {
            return Result.success("suchess")
                    .withValue(protocolParams)
                    .code(200);
        } else {
            result = Result.error("Error fetching protocol params");
        }
        return result;
    }
}
