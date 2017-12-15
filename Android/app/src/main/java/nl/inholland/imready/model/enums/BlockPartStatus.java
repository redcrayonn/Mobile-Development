package nl.inholland.imready.model.enums;

import com.google.gson.annotations.SerializedName;

public enum BlockPartStatus {
    @SerializedName("0")
    ONGOING,
    @SerializedName("1")
    PENDING,
    @SerializedName("2")
    DONE
}
