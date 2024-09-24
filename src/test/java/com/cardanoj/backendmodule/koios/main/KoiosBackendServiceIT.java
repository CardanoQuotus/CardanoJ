package com.cardanoj.backendmodule.koios.main;

import com.cardanoj.backend.api.BackendService;
import com.cardanoj.backend.api.NetworkInfoService;
import com.cardanoj.backend.model.Genesis;
import com.cardanoj.coreapi.exception.ApiException;
import com.cardanoj.coreapi.model.Result;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class KoiosBackendServiceIT {

    @Test
    void testCreatePreprodBackendServiceWithUrl() throws ApiException {
        BackendService backendService = new KoiosBackendService(Constants.KOIOS_PREPROD_URL);
        NetworkInfoService networkInfoService = backendService.getNetworkInfoService();

        getNetworkInfoAndCompare(networkInfoService);
    }

    public void getNetworkInfoAndCompare(NetworkInfoService networkInfoService) throws ApiException {
        Result<Genesis> genesisResult = networkInfoService.getNetworkInfo();

        Genesis genesis = genesisResult.getValue();
        assertNotNull(genesis);
        assertEquals(0.05,genesis.getActiveSlotsCoefficient().doubleValue());
        assertEquals(432000,genesis.getEpochLength());
        assertEquals(62, genesis.getMaxKesEvolutions());
        assertEquals("45000000000000000", genesis.getMaxLovelaceSupply());
        assertEquals(1, genesis.getNetworkMagic());
        assertEquals(2160, genesis.getSecurityParam());
        assertEquals(1, genesis.getSlotLength());
        assertEquals(129600, genesis.getSlotsPerKesPeriod());
    }
}
