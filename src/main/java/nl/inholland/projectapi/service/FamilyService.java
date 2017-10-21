package nl.inholland.projectapi.service;

import java.net.URI;
import java.security.Principal;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.UriInfo;
import nl.inholland.projectapi.model.APIKey;
import nl.inholland.projectapi.model.Client;
import nl.inholland.projectapi.model.inputs.Credentials;
import nl.inholland.projectapi.model.Family;
import nl.inholland.projectapi.persistence.ClientDAO;
import nl.inholland.projectapi.persistence.FamilyDAO;
import nl.inholland.projectapi.persistence.UserDAO;
import org.bson.types.ObjectId;
import org.mindrot.jbcrypt.BCrypt;

public class FamilyService extends BaseService {

    private final FamilyDAO dao;
    private final UserDAO userDAO;
    private final ClientDAO clientDAO;

    @Inject
    public FamilyService(FamilyDAO dao, UserDAO userDAO, ClientDAO clientDAO) {
        this.dao = dao;
        this.userDAO = userDAO;
        this.clientDAO = clientDAO;
    }

    public List<Family> getAll(int count) {
        List<Family> families = dao.getAllFamilies();
        requireResult(families, "Family Not Found");
        return reduceList(families, count);
    }

    public URI create(Credentials credentials, UriInfo uriInfo) {

        if (userDAO.getByUsername(credentials.getUsername()) != null) {
            throw new ClientErrorException(409);
        }
        Family family = new Family(credentials);
        dao.create(family);
        return buildUri(uriInfo, family.getId());
    }

    public Family getById(String id, Principal principal) {
        Family family = dao.getById(id);
        requireResult(family, "Family not found");
        checkPermissions(family, userDAO.getByUsername(principal.getName()));
        return family;
    }

    public void update(String userId, Credentials credentials, Principal principal) {
        Family family = getById(userId, principal);
        family.setUserName(credentials.getUsername());
        family.setPassword(BCrypt.hashpw(credentials.getPassword(), BCrypt.gensalt()));
        family.setApiKey(new APIKey());
        dao.update(family);
    }

    public void deleteById(ObjectId id) {
        Family family = dao.getById(id.toString());
        requireResult(family, "Family not found");

        //TODO A non-ideal solution --> create qeury-based deletion of client/family relationship
        for (Client client : clientDAO.getAllClients()) {
            for (Family f : client.getFamily()) {
                if (f.getId().equals(family.getId())) {
                    client.getFamily().remove(f);
                    clientDAO.update(client);
                }
            }
        }
    }
}
