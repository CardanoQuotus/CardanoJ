package com.cardanoj.jna.jna.cliApi.common;

import com.google.gson.annotations.SerializedName;

public class Build {

    @SerializedName("type")
    private String type;

    @SerializedName("description")
    private String description;

    @SerializedName("cborHex")
    private String cborHex;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCborHex() {
        return cborHex;
    }

    public void setCborHex(String cborHex) {
        this.cborHex = cborHex;
    }

    @Override
    public String toString() {
        return "Build{" +
                "type='" + type + '\'' +
                ", description='" + description + '\'' +
                ", cborHex='" + cborHex + '\'' +
                '}';
    }
}
