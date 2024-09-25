package com.cardanoj.quicktx.verifiers;

import com.cardanoj.coreapi.model.Amount;
import com.cardanoj.coreapi.util.AssetUtil;
import com.cardanoj.quicktx.Verifier;
import com.cardanoj.quicktx.VerifierException;
import com.cardanoj.transaction.spec.Transaction;
import com.cardanoj.util.Tuple;

import java.math.BigInteger;

import static com.cardanoj.common.CardanoConstants.LOVELACE;

/**
 * Verifies the output amount for a given address
 */
public class OutputAmountVerifier implements Verifier {

    private final String address;
    private final Amount amount;
    private final String customMsg;

    /**
     * Verifies the output amount for a given address
     * @param address address to verify
     * @param amount amount to verify
     * @param customMsg custom message to throw in case of verification failure
     */
    public OutputAmountVerifier(String address, Amount amount, String customMsg) {
        this.address = address;
        this.amount = amount;
        this.customMsg = customMsg;
    }

    @Override
    public void verify(Transaction txn) throws VerifierException {
        if (LOVELACE.equals(amount.getUnit())) {
            BigInteger lovelaceAmt = txn.getBody()
                    .getOutputs().stream()
                    .filter(o -> o.getAddress().equals(address))
                    .map(o -> o.getValue().getCoin())
                    .reduce(BigInteger.ZERO, (amount1, amount2) -> amount1.add(amount2));

            if (lovelaceAmt.compareTo(amount.getQuantity()) != 0) {
                String expectedMsg = formatExceptionMessage(customMsg, address, amount, lovelaceAmt);
                throw new VerifierException(expectedMsg);
            }
        } else {
            BigInteger assetAmount = txn.getBody()
                    .getOutputs().stream()
                    .filter(o -> o.getAddress().equals(address) && o.getValue().getMultiAssets() != null)
                    .flatMap(transactionOutput -> transactionOutput.getValue().getMultiAssets().stream())
                    .flatMap(multiAsset -> multiAsset.getAssets().stream().map(asset -> new Tuple<>(multiAsset.getPolicyId(), asset)))
                    .filter(assetTuple -> {
                        String unit = AssetUtil.getUnit(assetTuple._1, assetTuple._2);
                        return unit != null && unit.equals(amount.getUnit());
                    }).map(assetTuple -> assetTuple._2.getValue())
                    .reduce(BigInteger.ZERO, (amount1, amount2) -> amount1.add(amount2));

            String expectedMsg = formatExceptionMessage(customMsg, address, amount, assetAmount);
            if (assetAmount.compareTo(amount.getQuantity()) != 0) {
                throw new VerifierException(expectedMsg);
            }
        }
    }

    private String formatExceptionMessage(String customMsg, String address, Amount expectedAmount, BigInteger actualAmount) {
        String expectedMsg = String.format("Expected amount %s(%s) for address %s, \nbut got %s",
                expectedAmount.getQuantity(), expectedAmount.getUnit(), address, actualAmount);
        if(customMsg != null)
            expectedMsg = customMsg + ".\n" + expectedMsg;

        return expectedMsg;
    }
}
