package com.cardanoj.quicktx.verifiers;

import com.cardanoj.coreapi.model.Amount;
import com.cardanoj.quicktx.Verifier;

/**
 * Helper class to create verifiers
 */
public class TxVerifiers {

    /**
     * Verifier to verify the output amount for a given address
     * @param address address to verify
     * @param amount amount to verify
     * @return Verifier
     */
    public static Verifier outputAmountVerifier(String address, Amount amount) {
        return new OutputAmountVerifier(address, amount, null);
    }

    /**
     * Verifier to verify the output amount for a given address
     * @param address address to verify
     * @param amount amount to verify
     * @param customMsg custom message to throw in case of verification failure
     * @return Verifier
     */
    public static Verifier outputAmountVerifier(String address, Amount amount, String customMsg) {
        return new OutputAmountVerifier(address, amount, customMsg);
    }
}
