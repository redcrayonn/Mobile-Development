package nl.inholland.imready.model.user;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Caregiver extends User {
    @SerializedName("Clients")
    private List<Client> clients;

    public List<Client> getClients() {
        return clients;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }
}
