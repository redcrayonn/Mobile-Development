package nl.inholland.projectapi.resource;

import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import nl.inholland.projectapi.model.Client;
import nl.inholland.projectapi.presentation.ClientPresenter;
import nl.inholland.projectapi.presentation.model.ClientView;
import nl.inholland.projectapi.service.ClientService;

@Path("/clients")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
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
    public Response getAll() {
        List<Client> clients = clientService.getAllClients();
        if (clients.isEmpty()) {
            throw new WebApplicationException("Niet gevonden", 404);
        }
        List<ClientView> clientView = clientPresenter.present(clients);
        return Response.ok(clientView, MediaType.APPLICATION_JSON).build();
    }
    
    @GET
    @Path("/{userId}")
    public Response getById(@PathParam("userId")String userId) {
        Client client = clientService.getClientById(userId);
        if (client == null) {
            throw new WebApplicationException("Niet gevonden", 404);
        }
        ClientView clientView = clientPresenter.present(client);
        return Response.ok(clientView, MediaType.APPLICATION_JSON).build();
    }
}

