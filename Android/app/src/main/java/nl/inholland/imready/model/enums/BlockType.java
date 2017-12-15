package nl.inholland.imready.model.enums;

import com.google.gson.annotations.SerializedName;

public enum BlockType {
    @SerializedName("0")
    LIVING,
    @SerializedName("1")
    INSURANCE,
    @SerializedName("2")
    MONEY,
    @SerializedName("3")
    HEALTH,
    @SerializedName("4")
    SOCIAL,
    @SerializedName("5")
    WORK,
    @SerializedName("6")
    EDUCATION,
    @SerializedName("7")
    FAMILY,
    @SerializedName("8")
    RIGHTSANDOBLIGATIONS,
    @SerializedName("9")
    TREATMENTPLAN,

    ADD
}
