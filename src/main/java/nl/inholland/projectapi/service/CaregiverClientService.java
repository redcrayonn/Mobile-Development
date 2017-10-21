package nl.inholland.projectapi.service;

import java.util.List;
import javax.inject.Inject;
import nl.inholland.projectapi.model.Caregiver;
import nl.inholland.projectapi.model.Client;
import nl.inholland.projectapi.persistence.ClientDAO;

public class CaregiverClientService extends BaseService{
    
    private final ClientDAO clientDAO;

    @Inject
    public CaregiverClientService(ClientDAO clientDAO) {
        this.clientDAO = clientDAO;
    }
    
    public List<Client> getAll(Caregiver caregiver) {
        requireResult(caregiver, "Caregiver not found");
        return clientDAO.getByCaregiverId(caregiver.getId());
    }
    
}
