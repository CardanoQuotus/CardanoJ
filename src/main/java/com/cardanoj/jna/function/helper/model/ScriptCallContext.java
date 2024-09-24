package com.cardanoj.jna.function.helper.model;

import com.cardanoj.jna.coreapi.model.Utxo;
import com.cardanoj.jna.plutus.spec.ExUnits;
import com.cardanoj.jna.plutus.spec.PlutusScript;
import com.cardanoj.jna.plutus.spec.RedeemerTag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A class for Plutus script specific data used in transaction
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScriptCallContext<T, K> {
    private PlutusScript script;
    private Utxo utxo;
    private T datum;
    private K redeemer;

    @Builder.Default
    private RedeemerTag redeemerTag = RedeemerTag.Spend;

    private ExUnits exUnits;
}
