package nl.inholland.imready.model.user;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import nl.inholland.imready.service.deserializer.PostProcessingEnabler;

public class Caregiver extends User implements PostProcessingEnabler.PostProcessable {
    @SerializedName("Clients")
    private List<Client> clients = new ArrayList<>();

    public List<Client> getClients() {
        return clients;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }

    @Override
    public void gsonPostProcess() {
        if (getClients() != null) {
            for (Client client : getClients()) {
                client.setCaregiver(this);
            }
        }
    }
}
