package nl.inholland.projectapi.service;

import java.net.URI;
import java.security.Principal;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.core.UriInfo;
import nl.inholland.projectapi.model.APIKey;
import nl.inholland.projectapi.model.Caregiver;
import nl.inholland.projectapi.model.Client;
import nl.inholland.projectapi.model.Credentials;
import nl.inholland.projectapi.persistence.BlockDAO;
import nl.inholland.projectapi.persistence.CaregiverDAO;
import nl.inholland.projectapi.persistence.ClientDAO;
import nl.inholland.projectapi.persistence.UserDAO;
import org.bson.types.ObjectId;
import org.mindrot.jbcrypt.BCrypt;

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
        
         //IF PERMISSION IS FIXED, UNDO COMMENT !!
        // checkPermissions(caregiver, userDAO.getByUsername(principal.getName()));
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

    public void update(String userId, Credentials credentials, Principal principal) {
        Caregiver caregiver = getById(userId, principal);
        caregiver.setUserName(credentials.getUsername());
        caregiver.setPassword(BCrypt.hashpw(credentials.getPassword(), BCrypt.gensalt()));
        caregiver.setApiKey(new APIKey());
        caregiverDAO.update(caregiver);
    }

    public void deleteById(ObjectId id) {
        Caregiver caregiver = caregiverDAO.getById(id.toHexString());
        requireResult(caregiver, "Caregiver not found");

        //TODO A non-ideal solution --> create qeury-based deletion of client/family relationship
        try {
            for (Client client : clientDAO.getAllClients()) {
                for (int i = 0; i < client.getCaregivers().size(); i++) {
                    Caregiver c = client.getCaregivers().get(i);
                    if (c.getId().equals(caregiver.getId())) {
                        client.getCaregivers().remove(c);
                        clientDAO.update(client);
                    }
                }
            }
            caregiverDAO.delete(caregiver);
        } catch (ProcessingException e) {
            throw new ProcessingException(e);
        }
    }
}
