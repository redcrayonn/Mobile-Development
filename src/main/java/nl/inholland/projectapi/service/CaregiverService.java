package nl.inholland.projectapi.service;

import java.net.URI;
import java.security.Principal;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.UriInfo;
import nl.inholland.projectapi.model.APIKey;
import nl.inholland.projectapi.model.Caregiver;
import nl.inholland.projectapi.model.Client;
import nl.inholland.projectapi.model.inputs.Credentials;
import nl.inholland.projectapi.persistence.BlockDAO;
import nl.inholland.projectapi.persistence.CaregiverDAO;
import nl.inholland.projectapi.persistence.ClientDAO;
import nl.inholland.projectapi.persistence.UserDAO;
import org.bson.types.ObjectId;

public class CaregiverService extends BaseService {

    private final CaregiverDAO caregiverDAO;
    private final UserDAO userDAO;
    private final ClientDAO clientDAO;

    @Inject
    public CaregiverService(CaregiverDAO caregiverDAO, UserDAO userDAO, BlockDAO blockDAO, ClientDAO clientDAO) {
        this.caregiverDAO = caregiverDAO;
        this.userDAO = userDAO;
        this.clientDAO = clientDAO;
    }

    public List<Caregiver> getAll(int count) {
        List<Caregiver> caregivers = caregiverDAO.getAllCaregivers();
        requireResult(caregivers, "Not Found");
        return reduceList(caregivers, count);
    }

    public Caregiver getById(String id, Principal principal) {
        Caregiver caregiver = caregiverDAO.getById(id);
        requireResult(caregiver, "Caregiver not found");
        checkPermissions(caregiver, userDAO.getByUsername(principal.getName()));
        return caregiver;
    }

    public URI create(Credentials credentials, UriInfo uriInfo) {
        if (userDAO.getByUsername(credentials.getUsername()) != null) {
            throw new ClientErrorException(409);
        }
        Caregiver caregiver;
        try {
            caregiver = new Caregiver(credentials);
        } catch (Exception e) {
            throw new BadRequestException();
        }
        caregiverDAO.create(caregiver);
        return buildUri(uriInfo, caregiver.getId());
    }

    public void update(Caregiver caregiver, Credentials credentials) {
        caregiver.setUserName(credentials.getUsername());
        caregiver.setPassword(credentials.getPassword());
        caregiver.setApiKey(new APIKey());
        caregiverDAO.update(caregiver);
    }

    public void deleteById(ObjectId id) {
        Caregiver caregiver = caregiverDAO.getById(id.toString());
        requireResult(caregiver, "Caregiver not found");

        for (Client client : clientDAO.getAllClients()) {
            client.getCaregivers().removeIf(c -> c.getId().equals(caregiver.getId()));
        }
        caregiverDAO.delete(caregiver);
    }
}
