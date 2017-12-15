package nl.inholland.imready.model.enums;

import com.google.gson.annotations.SerializedName;

public enum UserRole {
    @SerializedName(value = "ADMIN", alternate = {"0"})
    ADMIN,
    @SerializedName(value = "CLIENT", alternate = {"1"})
    CLIENT,
    @SerializedName(value = "CAREGIVER", alternate = {"2"})
    CAREGIVER,
    @SerializedName(value = "FAMILY", alternate = {"3"})
    FAMILY
}
