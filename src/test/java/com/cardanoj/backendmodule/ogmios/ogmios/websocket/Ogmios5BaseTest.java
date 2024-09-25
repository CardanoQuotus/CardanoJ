package com.cardanoj.backendmodule.ogmios.ogmios.websocket;

import com.cardanoj.backendmodule.ogmios.KupmiosBackendService;
import com.cardanoj.backendmodule.ogmios.kupo.KupoUtxoService;


public class Ogmios5BaseTest {
    protected Ogmios5BackendService ogmios5BackendService;
    protected KupoUtxoService kupoUtxoService;
    protected KupmiosBackendService kupmiosBackendService;

    public Ogmios5BaseTest() {
        this.ogmios5BackendService = new Ogmios5BackendService("ws://ogmios-preprod:1337/");
        this.kupoUtxoService = new KupoUtxoService("http://ogmios-preprod:1442");
    }
}
