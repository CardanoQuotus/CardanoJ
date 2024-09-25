package com.cardanoj.supplier.ogmios;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Getter
@Slf4j
public class OgmiosBaseService {

    private final String baseUrl;

    public OgmiosBaseService(String baseUrl) {
        this.baseUrl = baseUrl;

        if (log.isDebugEnabled()) {
            log.debug("Ogmios URL : " + baseUrl);
        }
    }

    protected Retrofit getRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(getBaseUrl())
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
    }

}
