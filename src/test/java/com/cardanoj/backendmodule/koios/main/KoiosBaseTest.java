package com.cardanoj.backendmodule.koios.main;

public class KoiosBaseTest {

    protected KoiosBackendService backendService;

    public KoiosBaseTest() {
        backendService = new KoiosBackendService(Constants.KOIOS_PREPROD_URL);
    }
}
