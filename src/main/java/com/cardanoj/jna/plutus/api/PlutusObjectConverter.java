package com.cardanoj.jna.plutus.api;

import com.cardanoj.jna.plutus.spec.PlutusData;

public interface PlutusObjectConverter {

    PlutusData toPlutusData(Object o);

}
