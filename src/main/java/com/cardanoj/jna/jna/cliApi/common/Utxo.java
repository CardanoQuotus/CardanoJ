package com.cardanoj.jna.jna.cliApi.common;

import com.google.gson.annotations.SerializedName;

public class Utxo {
    @SerializedName("txHash")
    private String txHash;

    @SerializedName("txIx")
    private String txIx;

    @SerializedName("amount")
    private String amount;

    @SerializedName("additionalInfo")
    private String additionalInfo;

    // Getters and setters

    public String getTxHash() {
        return txHash;
    }

    public void setTxHash(String txHash) {
        this.txHash = txHash;
    }

    public String getTxIx() {
        return txIx;
    }

    public void setTxIx(String txIx) {
        this.txIx = txIx;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

//    @Override
//    public String toString() {
//        return "Utxo{" +
//                "txHash='" + txHash + '\'' +
//                ", txIx='" + txIx + '\'' +
//                ", amount='" + amount + '\'' +
//                '}';
//    }
}