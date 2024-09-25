package com.cardanoj.backendmodule.ogmios;

import com.cardanoj.backend.api.UtxoService;
import com.cardanoj.backendmodule.ogmios.kupo.KupoUtxoService;
import com.cardanoj.backendmodule.ogmios.ogmios.http.OgmiosBackendService;

/**
 * KupmiosBackendService is a combination of Kupo and Ogmios backend services.
 * It uses Kupo for UtxoService and Ogmios for other services.
 */
public class KupmiosBackendService extends OgmiosBackendService {
    private final UtxoService kupoUtxoService;

    public KupmiosBackendService(String ogmiosHttpUrl, String kupoHttpUrl) {
        super(ogmiosHttpUrl);
        kupoUtxoService = new KupoUtxoService(kupoHttpUrl);
    }

    @Override
    public UtxoService getUtxoService() {
        return kupoUtxoService;
    }

}