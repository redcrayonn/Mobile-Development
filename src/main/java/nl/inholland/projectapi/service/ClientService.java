package nl.inholland.projectapi.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import java.io.IOException;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import nl.inholland.projectapi.model.Client;
import nl.inholland.projectapi.model.Credentials;
import nl.inholland.projectapi.persistence.ClientDAO;
import nl.inholland.projectapi.persistence.UserDAO;
import org.bson.types.ObjectId;

public class ClientService extends BaseService {

    private final ClientDAO dao;
    private final UserDAO userDAO;

    @Inject
    public ClientService(ClientDAO dao, UserDAO userDAO) {
        this.dao = dao;
        this.userDAO = userDAO;
    }

    public List<Client> getAll() {
        List<Client> clients = dao.getAllClients();
        if (clients.isEmpty()) {
            throw new NotFoundException("Not found");
        }
        return clients;
    }

    public UriBuilder create(Credentials credentials, UriInfo uriInfo) {
        if (userDAO.getByUsername(credentials.getUsername()) != null) {
            throw new ClientErrorException(409);
        }

        Client client;
        try {
            client = new Client(credentials);
        } catch (Exception e) {
            throw new BadRequestException();
        }
        dao.create(client);
        return buildUri(uriInfo, client.getId());
    }

    public Client getById(String id) {
        Client client = dao.get(id);
        if (client == null) {
            throw new NotFoundException("Not found");
        }
        return client;
    }

    public void patch(String userId, JsonNode patchRequest) {
        JsonNode patched = null;
        try {
            for (int i = 0; i < patchRequest.size(); i++) {
                if (patchRequest.get(i).get("path").asText().equals("/password")) {
                    throw new BadRequestException("Bad patch request");
                }
            }
            Client client = getById(userId);//Get client to be patched from userId
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
        getById(id.toHexString());
        dao.deleteById(id);
    }
}
