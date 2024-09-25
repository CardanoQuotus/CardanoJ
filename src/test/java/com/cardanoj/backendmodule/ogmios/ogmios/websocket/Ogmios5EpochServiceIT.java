package com.cardanoj.backendmodule.ogmios.ogmios.websocket;

import com.cardanoj.backend.api.EpochService;
import com.cardanoj.coreapi.exception.ApiException;
import com.cardanoj.coreapi.model.ProtocolParams;
import com.cardanoj.coreapi.model.Result;
import com.cardanoj.util.JsonUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Ogmios5EpochServiceIT extends Ogmios5BaseTest {

    EpochService epochService;

    @BeforeEach
    public void setup() {
        epochService =  ogmios5BackendService.getEpochService();
    }

    @Test
    public void testGetLatestProtocolParameters() throws ApiException {
        Result<ProtocolParams> result = epochService.getProtocolParameters();

        ProtocolParams protocolParams = result.getValue();
        System.out.println(JsonUtil.getPrettyJson(protocolParams));

        assertThat(protocolParams).isNotNull();
        assertThat(protocolParams.getPoolDeposit()).isEqualTo("500000000");
        assertThat(protocolParams.getCoinsPerUtxoSize()).isEqualTo("4310");
        assertThat(protocolParams.getEMax()).isNotNull();
        assertThat(protocolParams.getNOpt()).isNotNull();
    }
}
