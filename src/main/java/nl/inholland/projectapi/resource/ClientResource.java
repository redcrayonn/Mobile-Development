package nl.inholland.projectapi.resource;

import com.fasterxml.jackson.databind.JsonNode;
import io.dropwizard.jersey.PATCH;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import nl.inholland.projectapi.model.Client;
import nl.inholland.projectapi.model.Credentials;
import nl.inholland.projectapi.model.Role;
import nl.inholland.projectapi.model.Secured;
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
    @Secured({Role.client})
    public List<ClientView> getAll() {
        List<Client> clients = clientService.getAll();
        return clientPresenter.present(clients);
    }
    @POST
    @Consumes("application/json")
    public Response create(Credentials credentials, @Context UriInfo uriInfo)
    {
        UriBuilder builder = clientService.create(credentials, uriInfo);
        return Response.created(builder.build()).build();
    }
    @GET
    @Path("/{userId}")
    @Produces("application/json")
    public ClientView getById(@PathParam("userId") String userId) {
        Client client = clientService.getById(userId);
        return clientPresenter.present(client);
    }
    
    @PATCH
    @Path("/{userId}")
    @Consumes("application/json")
    public Response patch(@PathParam("userId") String userId, JsonNode patchRequest) {
        clientService.patch(userId, patchRequest); 
        return Response.ok().build();//Return 200
    }  
}
    

