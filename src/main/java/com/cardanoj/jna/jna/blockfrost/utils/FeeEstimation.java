package com.cardanoj.jna.jna.blockfrost.utils;

import org.json.JSONObject;
import java.math.BigInteger;

public class FeeEstimation {
    public static BigInteger estimateFee(int txSize, JSONObject protocolParameters) {
        BigInteger minFeeA = protocolParameters.getBigInteger("min_fee_a");
        BigInteger minFeeB = protocolParameters.getBigInteger("min_fee_b");
        return minFeeA.multiply(BigInteger.valueOf(txSize)).add(minFeeB);
    }
}
