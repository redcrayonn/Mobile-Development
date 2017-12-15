package nl.inholland.imready.model.user;

import com.google.gson.annotations.SerializedName;

public class Relative extends User {
    @SerializedName("Client")
    private Client client;
}
