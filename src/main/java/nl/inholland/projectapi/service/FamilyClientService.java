package nl.inholland.projectapi.service;

import java.util.List;
import javax.inject.Inject;
import nl.inholland.projectapi.model.Client;
import nl.inholland.projectapi.model.Family;
import nl.inholland.projectapi.persistence.ClientDAO;

public class FamilyClientService extends BaseService {

    private final ClientDAO clientDAO;

    @Inject
    public FamilyClientService(ClientDAO clientDAO) {
        this.clientDAO = clientDAO;
    }

    public List<Client> getAll(Family family) {
        requireResult(family, "Family member not found");
        return clientDAO.getByFamilyId(family.getId());
    }
}
