package nl.inholland.imready.model.user;

public class Family extends User {
    private Client client;

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
