package com.cardanoj.backendmodule.koios.main;

import com.cardanoj.backend.api.EpochService;
import com.cardanoj.backend.model.EpochContent;
import com.cardanoj.coreapi.exception.ApiException;
import com.cardanoj.coreapi.model.ProtocolParams;
import com.cardanoj.coreapi.model.Result;
import com.cardanoj.util.JsonUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;

class KoiosEpochServiceIT extends KoiosBaseTest {

    private EpochService epochService;

    @BeforeEach
    public void setup() {
        epochService = backendService.getEpochService();
    }

    @Test
    void testGetLatestEpoch() throws ApiException {
        Result<EpochContent> result = epochService.getLatestEpoch();

        EpochContent epochContent = result.getValue();

        System.out.println(result);
        System.out.println(JsonUtil.getPrettyJson(epochContent));

        assertThat(result.isSuccessful(), is(true));
        assertThat(epochContent.getEpoch(), not(0));
        assertThat(epochContent.getEpoch(), notNullValue());
    }

    @Test
    void testGetLatestEpochByNumber() throws ApiException {
        Result<EpochContent> result = epochService.getEpoch(37);

        EpochContent epochContent = result.getValue();

        System.out.println(result);
        System.out.println(JsonUtil.getPrettyJson(epochContent));

        assertThat(result.isSuccessful(), is(true));
        assertThat(epochContent.getEpoch(), is(37));
        assertThat(epochContent.getBlockCount(), greaterThan(0));
    }

    @Test
    void testGetProtocolParameters() throws ApiException {
        Result<ProtocolParams> result = epochService.getProtocolParameters(37);

        System.out.println(result);

        ProtocolParams protocolParams = result.getValue();
        System.out.println(JsonUtil.getPrettyJson(protocolParams));

        assertThat(protocolParams, notNullValue());
        assertThat(protocolParams.getMinUtxo(), is("0"));
        assertThat(protocolParams.getPoolDeposit(), is("500000000"));
    }

    @Test
    void testGetLatestProtocolParameters() throws ApiException {
        Result<ProtocolParams> result = epochService.getProtocolParameters();

        System.out.println(result);

        ProtocolParams protocolParams = result.getValue();
        System.out.println(JsonUtil.getPrettyJson(protocolParams));

        assertThat(protocolParams, notNullValue());
        assertThat(protocolParams, notNullValue());
        assertThat(protocolParams.getPoolDeposit(), is("500000000"));
        assertThat(protocolParams.getCoinsPerUtxoSize(), is("4310"));
        assertThat(protocolParams.getEMax(), notNullValue());
        assertThat(protocolParams.getNOpt(), notNullValue());
    }
}
