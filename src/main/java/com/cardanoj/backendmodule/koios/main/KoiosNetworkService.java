package com.cardanoj.backendmodule.koios.main;

import com.cardanoj.backend.api.NetworkInfoService;
import com.cardanoj.backend.model.Genesis;
import com.cardanoj.coreapi.model.Result;
import rest.koios.client.backend.api.network.NetworkService;

import java.math.BigDecimal;

/**
 * Koios Network Service
 */
public class KoiosNetworkService implements NetworkInfoService {

    /**
     * Network Service
     */
    private final NetworkService networkService;

    /**
     * KoiosNetworkService Constructor
     *
     * @param networkService networkService
     */
    public KoiosNetworkService(NetworkService networkService) {
        this.networkService = networkService;
    }

    @Override
    public Result<Genesis> getNetworkInfo() {
        try {
            rest.koios.client.backend.api.base.Result<rest.koios.client.backend.api.network.model.Genesis> genesisInfoResult = networkService.getGenesisInfo();
            if (!genesisInfoResult.isSuccessful()) {
                return Result.error(genesisInfoResult.getResponse()).code(genesisInfoResult.getCode());
            }
            rest.koios.client.backend.api.network.model.Genesis gen = genesisInfoResult.getValue();
            Genesis genesis = new Genesis();
            genesis.setActiveSlotsCoefficient(new BigDecimal(gen.getActiveslotcoeff()));
            genesis.setUpdateQuorum(Integer.valueOf(gen.getUpdatequorum()));
            genesis.setMaxLovelaceSupply(gen.getMaxlovelacesupply());
            genesis.setNetworkMagic(Integer.valueOf(gen.getNetworkmagic()));
            genesis.setEpochLength(Integer.valueOf(gen.getEpochlength()));
            genesis.setSystemStart(gen.getSystemstart());
            genesis.setSlotsPerKesPeriod(Integer.valueOf(gen.getSlotsperkesperiod()));
            genesis.setSlotLength(Integer.valueOf(gen.getSlotlength()));
            genesis.setMaxKesEvolutions(Integer.valueOf(gen.getMaxkesrevolutions()));
            genesis.setSecurityParam(Integer.valueOf(gen.getSecurityparam()));
            return Result.success("OK").withValue(genesis).code(200);
        } catch (rest.koios.client.backend.api.base.exception.ApiException e) {
            return Result.error(e.getMessage());
        }
    }
}
