package com.cardanoj.supplier.ogmios;

import com.cardanoj.supplier.kupo.KupoUtxoSupplier;

public class OgmiosBaseTest {
    protected OgmiosProtocolParamSupplier ogmiosProtocolParamSupplier;
    protected OgmiosTransactionProcessor ogmiosTransactionProcessor;
    protected KupoUtxoSupplier kupoUtxoSupplier;

    private final static String OGMIOS_URL = "https://preprod-v5.ogmios-m1.demeter.run:1337/";
    private final static String KUPO_URL = "https://preprod-v2.kupo-m1.demeter.run:1442/";

    public OgmiosBaseTest() {
        this.ogmiosProtocolParamSupplier = new OgmiosProtocolParamSupplier(OGMIOS_URL);
        this.ogmiosTransactionProcessor = new OgmiosTransactionProcessor(OGMIOS_URL);
        this.kupoUtxoSupplier = new KupoUtxoSupplier(KUPO_URL);
    }
}
