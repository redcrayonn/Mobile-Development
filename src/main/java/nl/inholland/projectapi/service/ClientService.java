package nl.inholland.projectapi.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import nl.inholland.projectapi.model.APIKey;
import nl.inholland.projectapi.model.Client;
import nl.inholland.projectapi.model.Credentials;
import nl.inholland.projectapi.persistence.BlockDAO;
import nl.inholland.projectapi.persistence.ClientDAO;
import nl.inholland.projectapi.persistence.UserDAO;
import org.bson.types.ObjectId;
import org.mindrot.jbcrypt.BCrypt;

public class ClientService extends BaseService {

    private final ClientDAO dao;
    private final UserDAO userDAO;
    private final BlockDAO blockDAO;

    @Inject
    public ClientService(ClientDAO dao, UserDAO userDAO, BlockDAO blockDAO) {
        this.dao = dao;
        this.userDAO = userDAO;
        this.blockDAO = blockDAO;
    }

    public List<Client> getAll() {
        List<Client> clients = dao.getAllClients();
        requireResult(clients, "Not Found");
        return clients;
    }

    public UriBuilder create(Credentials credentials, UriInfo uriInfo) {
        if (userDAO.getByUsername(credentials.getUsername()) != null) {
            throw new ClientErrorException(409);
        }
        Client client;
        try {
            client = new Client(credentials);        
            client.setBuildingBlocks(blockDAO.getAll());
        } catch (Exception e) {
            throw new BadRequestException();
        }
        dao.create(client);
        return buildUri(uriInfo, client.getId());
    }
    
    public Client getById(String id, Principal principal) {
        Client client = dao.get(id);
        requireResult(client, "Client not found");   
        checkPermissions(client, userDAO.getByUsername(principal.getName()));
        return client;
    }
    public void update(String userId, Credentials credentials, Principal principal) {
        Client client = getById(userId, principal);
        client.setUserName(credentials.getUsername());
        client.setPassword(BCrypt.hashpw(credentials.getPassword(), BCrypt.gensalt()));
        client.setApiKey(new APIKey());
        dao.update(client);
    }
    public void patch(String userId, JsonNode patchRequest, Principal principal) {
        JsonNode patched = null;
        try {
            for (int i = 0; i < patchRequest.size(); i++) {
                if (patchRequest.get(i).get("path").asText().equals("/password")) {
                    throw new BadRequestException("Bad patch request, use PUT for password changes");
                }
            }
            Client client = dao.get(userId);
            requireResult(client, "Client not found");//Get client to be patched from userId
            checkPermissions(client, userDAO.getByUsername(principal.getName()));
            ObjectMapper mapper = new ObjectMapper();//Map client to json
            JsonNode jsonClient = mapper.valueToTree(client);//map client to json
            JsonPatch patch = JsonPatch.fromJson(patchRequest);//Get patchrequest from body
            patched = patch.apply(jsonClient);//Patch client        
            dao.update(mapper.treeToValue(patched, Client.class));//Send patch to database as Client object

        } catch (JsonPatchException | IOException | IllegalArgumentException | NullPointerException ex) {
            throw new BadRequestException("Bad patch request");
        }
    }

    public void deleteById(ObjectId id) {
        Client client = dao.get(id);
        requireResult(client, "Client not found");
        dao.delete(client);
    }
}
