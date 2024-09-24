package com.cardanoj.jna.backend.factory;

import com.cardanoj.jna.backend.api.BackendService;
import com.cardanoj.jna.blockfrost.service.BFBackendService;

public class BackendFactory {

    /**
     * Get BackendService for Blockfrost
     * @param blockfrostUrl
     * @param projectId
     * @return
     */
    public static BackendService getBlockfrostBackendService(String blockfrostUrl, String projectId) {
        return new BFBackendService(blockfrostUrl, projectId);
    }

}
