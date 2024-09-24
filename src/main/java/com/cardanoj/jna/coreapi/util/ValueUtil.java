package com.cardanoj.jna.coreapi.util;

import com.cardanoj.jna.coreapi.model.Amount;
import com.cardanoj.jna.transaction.spec.MultiAsset;
import com.cardanoj.jna.transaction.spec.Value;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;

import static com.cardanoj.jna.common.CardanoConstants.LOVELACE;

/**
 * Utility class for {@link Value}
 */
public class ValueUtil {

    /**
     * Converts {@link Value} to {@link Amount} list
     * @param value {@link Value}
     * @return {@link Amount} list
     */
    public static List<Amount> toAmountList(@NonNull Value value) {
        List<Amount> amounts = new ArrayList<>();
        amounts.add(new Amount(LOVELACE, value.getCoin()));
        for (MultiAsset multiAsset : value.getMultiAssets()) {
            String policyId = multiAsset.getPolicyId();
            for (com.cardanoj.jna.transaction.spec.Asset asset : multiAsset.getAssets()) {
                amounts.add(new Amount(policyId + asset.getNameAsHex().replace("0x", ""), asset.getValue()));
            }
        }
        return amounts;
    }
}
