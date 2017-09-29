package nl.inholland.projectapi.service;

import java.util.List;
import javax.inject.Inject;
import nl.inholland.projectapi.model.Client;
import nl.inholland.projectapi.persistence.ClientDAO;

public class ClientService extends BaseService {

    private final ClientDAO dao;

    @Inject
    public ClientService(ClientDAO dao) {
        this.dao = dao;
    }

    public List<Client> getAllClients() {
        return dao.getAll();
    }

    public Client getClientById(String id) {
        return dao.get(id);
    }
    public void patch(Client client) {
        dao.update(client);
    }
}
