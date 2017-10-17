package nl.inholland.projectapi.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import java.io.IOException;
import java.net.URI;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.UriInfo;
import nl.inholland.projectapi.model.APIKey;
import nl.inholland.projectapi.model.Activity;
import nl.inholland.projectapi.model.BuildingBlock;
import nl.inholland.projectapi.model.Client;
import nl.inholland.projectapi.model.Credentials;
import nl.inholland.projectapi.persistence.BlockDAO;
import nl.inholland.projectapi.persistence.ClientDAO;
import nl.inholland.projectapi.persistence.UserDAO;
import org.bson.types.ObjectId;

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

    public List<Client> getAll(int count) {

        List<Client> clients = dao.getAllClients();
        requireResult(clients, "Not Found");
        return reduceList(clients, count);
    }

    public URI create(Credentials credentials, UriInfo uriInfo) {
        if (userDAO.getByUsername(credentials.getUsername()) != null) {
            throw new ClientErrorException(409);
        }
        Client client;
        try {
            client = new Client(credentials);
            List<BuildingBlock> blocks = new ArrayList<BuildingBlock>(blockDAO.getAll());
            for(BuildingBlock b : blocks) {
                b.setId(new ObjectId());
                for(Activity a : b.getActivities()) {
                    a.setId(new ObjectId());
                }
            }
            client.setBuildingBlocks(blocks);
        } catch (Exception e) {
            throw new BadRequestException();
        }
        dao.create(client);
        return buildUri(uriInfo, client.getId());
    }
    
    public Client getById(String id, Principal principal) {
        Client client = dao.getById(id);
        requireResult(client, "Client not found");   
        checkPermissions(client, userDAO.getByUsername(principal.getName()));
        return client;
    }
    
    public void update(String userId, Credentials credentials, Principal principal) {
        Client client = getById(userId, principal);
        client.setUserName(credentials.getUsername());
        client.setPassword(credentials.getPassword());
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
            Client client = dao.getById(userId);
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
        Client client = dao.getById(id.toHexString());
        requireResult(client, "Client not found");
        dao.delete(client);
    }
}
