package nl.inholland.projectapi.resource;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import io.dropwizard.jersey.PATCH;
import java.io.IOException;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import nl.inholland.projectapi.model.Client;
import nl.inholland.projectapi.presentation.ClientPresenter;
import nl.inholland.projectapi.presentation.model.ClientView;
import nl.inholland.projectapi.service.ClientService;

@Path("/api/v1/clients")
public class ClientResource extends BaseResource {

    private final ClientService clientService;
    private final ClientPresenter clientPresenter;

    @Inject
    public ClientResource(ClientService clientService, ClientPresenter clientPresenter) {
        this.clientService = clientService;
        this.clientPresenter = clientPresenter;
    }

    @GET
    @Produces("application/json")
    public List<ClientView> getAll() {
        List<Client> clients = clientService.getAllClients();
        if (clients.isEmpty()) {
            throw new WebApplicationException("Niet gevonden", 404);
        }
        return clientPresenter.present(clients);
    }

    @GET
    @Path("/{userId}")
    @Produces("application/json")
    public ClientView getById(@PathParam("userId") String userId) {
        Client client = clientService.getClientById(userId);
        if (client == null) {
            throw new WebApplicationException("Niet gevonden", 404);
        }
        return clientPresenter.present(client);
    }
    
    @PATCH
    @Path("/{userId}")
    @Consumes("application/json")
    public Response patch(@PathParam("userId") String userId, JsonNode patchRequest) {
        JsonNode patched = null;
        try {
            Client client = clientService.getClientById(userId);//Get client to be patched from userId
            ObjectMapper mapper = new ObjectMapper();//Map client to json
            JsonNode jsonClient = mapper.valueToTree(client);//map client to json
            JsonPatch patch = JsonPatch.fromJson(patchRequest);//Get patchrequest from body
            patched = patch.apply(jsonClient);//Patch client        
            clientService.patch(mapper.treeToValue(patched, Client.class));//Send patch to database as Client object
            
        } catch (JsonPatchException | IOException | IllegalArgumentException ex) {
            throw new WebApplicationException("Bad patch request", 400);
        }
        return Response.ok().build();//Return 200
    }  
}
    

