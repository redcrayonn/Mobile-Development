package nl.inholland.projectapi.service;

import java.net.URI;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.UriInfo;
import nl.inholland.projectapi.model.Caregiver;
import nl.inholland.projectapi.model.Client;
import nl.inholland.projectapi.model.EntityModel;
import nl.inholland.projectapi.persistence.CaregiverDAO;
import nl.inholland.projectapi.persistence.ClientDAO;

public class ClientCaregiverService extends BaseService {

    private final ClientDAO clientDAO;
    private final CaregiverDAO caregiverDAO;

    @Inject
    public ClientCaregiverService(ClientDAO clientDAO, CaregiverDAO caregiverDAO) {
        this.clientDAO = clientDAO;
        this.caregiverDAO = caregiverDAO;
    }

    public List<Caregiver> getAll(Client client, int count) {
        List<Caregiver> caregivers = client.getCaregivers();
        requireResult(caregivers, "Not Found");
        return reduceList(caregivers, count);
    }

    public URI create(Client client, EntityModel caregiver, UriInfo uriInfo) {
        if (caregiverDAO.getById(caregiver.getId()) != null) {
            client.getCaregivers().add(caregiverDAO.get(caregiver.getId()));
            clientDAO.update(client);
            return buildUri(uriInfo, caregiver.getId());
        }
        throw new NotFoundException("Caregiver not found");
    }

    public Caregiver get(Client client, String caregiverId) {
        for (Caregiver c : getAll(client, -1)) {
            if (c.getId().equals(caregiverId)) {
                return c;
            }
        }
        throw new NotFoundException("Caregiver not found");
    }

    public void update(Client client, String caregiverId, EntityModel input) {
        Caregiver newCaregiver = caregiverDAO.getById(input.getId());
        requireResult(newCaregiver, "Caregiver not found");
        for (Caregiver c : client.getCaregivers()) {
            if (c.getId().equals(caregiverId)) {  
                client.getCaregivers().remove(c);
                client.getCaregivers().add(newCaregiver);
                clientDAO.update(client);
                return;
            }
        }
        throw new NotFoundException("Caregiver not found");
    }

    public void delete(Client client, String caregiverId) {
        if (!client.getCaregivers().removeIf(c -> c.getId().equals(caregiverId))) {
            throw new NotFoundException("Caregiver not found");
        }
        clientDAO.update(client);
    }
}
