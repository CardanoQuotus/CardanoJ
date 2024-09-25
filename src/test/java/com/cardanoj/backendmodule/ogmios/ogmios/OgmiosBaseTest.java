package com.cardanoj.backendmodule.ogmios.ogmios;

import com.cardanoj.backendmodule.ogmios.KupmiosBackendService;
import com.cardanoj.backendmodule.ogmios.kupo.KupoUtxoService;
import com.cardanoj.backendmodule.ogmios.ogmios.http.OgmiosBackendService;

public class OgmiosBaseTest {
    protected OgmiosBackendService ogmiosBackendService;
    protected KupoUtxoService kupoUtxoService;
    protected KupmiosBackendService kupmiosBackendService;

    private String OGMIOS_HTTP_URL = "https://preprod-v5.ogmios-m1.demeter.run:1337/";
    private String KUPO_HTTP_URL = "https://preprod-v2.kupo-m1.demeter.run:1442/";

    public OgmiosBaseTest() {
        this.ogmiosBackendService = new OgmiosBackendService(OGMIOS_HTTP_URL);
        this.kupoUtxoService = new KupoUtxoService(KUPO_HTTP_URL);

        this.kupmiosBackendService = new KupmiosBackendService(OGMIOS_HTTP_URL, KUPO_HTTP_URL);
    }
}
