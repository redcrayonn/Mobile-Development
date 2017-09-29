package nl.inholland.projectapi.resource;

import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.WebApplicationException;
import nl.inholland.projectapi.model.Client;
import nl.inholland.projectapi.presentation.ClientPresenter;
import nl.inholland.projectapi.presentation.model.ClientView;
import nl.inholland.projectapi.service.ClientService;

@Path("/api/1.0/clients")
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
    public ClientView getById(@PathParam("userId") String userId) {
        Client client = clientService.getClientById(userId);
        if (client == null) {
            throw new WebApplicationException("Niet gevonden", 404);
        }
        return clientPresenter.present(client);
    }
}
