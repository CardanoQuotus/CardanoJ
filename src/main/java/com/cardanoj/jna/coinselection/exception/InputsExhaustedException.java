package com.cardanoj.jna.coinselection.exception;

import com.cardanoj.jna.coinselection.exception.base.CoinSelectionException;

public class InputsExhaustedException extends CoinSelectionException {

    public InputsExhaustedException() {
        super("INPUTS_EXHAUSTED");
    }
}
