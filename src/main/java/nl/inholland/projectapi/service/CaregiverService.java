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
import nl.inholland.projectapi.model.Credentials;
import nl.inholland.projectapi.persistence.BlockDAO;
import nl.inholland.projectapi.persistence.CaregiverDAO;
import nl.inholland.projectapi.persistence.UserDAO;
import org.bson.types.ObjectId;
import org.mindrot.jbcrypt.BCrypt;

public class CaregiverService extends BaseService {

    private final CaregiverDAO dao;
    private final UserDAO userDAO;

    @Inject
    public CaregiverService(CaregiverDAO dao, UserDAO userDAO, BlockDAO blockDAO) {
        this.dao = dao;
        this.userDAO = userDAO;
    }
    
    public List<Caregiver> getAll(int count) {
        List<Caregiver> caregivers = dao.getAllCaregivers();
        requireResult(caregivers, "Not Found");
        return reduceList(caregivers, count);
    }
   
    public Caregiver getById(String id, Principal principal) {
        Caregiver caregiver = dao.getById(id);
        requireResult(caregiver, "Caregiver not found");   
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
        dao.create(caregiver);
        return buildUri(uriInfo, caregiver.getId());
    }
    
    public void update(String userId, Credentials credentials, Principal principal) {
        Caregiver caregiver = getById(userId, principal);
        caregiver.setUserName(credentials.getUsername());
        caregiver.setPassword(BCrypt.hashpw(credentials.getPassword(), BCrypt.gensalt()));
        caregiver.setApiKey(new APIKey());
        dao.update(caregiver);
    }

    public void deleteById(ObjectId id) {
        Caregiver caregiver = dao.getById(id.toHexString());
        requireResult(caregiver, "Caregiver not found");
        dao.delete(caregiver);
    }
}
