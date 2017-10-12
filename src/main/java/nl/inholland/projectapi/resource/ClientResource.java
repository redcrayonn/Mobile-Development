package nl.inholland.projectapi.resource;

import com.fasterxml.jackson.databind.JsonNode;
import io.dropwizard.jersey.PATCH;
import java.net.URI;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Produces;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import nl.inholland.projectapi.model.Client;
import nl.inholland.projectapi.model.Credentials;
import nl.inholland.projectapi.model.Role;
import nl.inholland.projectapi.model.Secured;
import nl.inholland.projectapi.presentation.ClientPresenter;
import nl.inholland.projectapi.presentation.model.ClientView;
import nl.inholland.projectapi.service.ClientService;
import org.bson.types.ObjectId;

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
    @Secured({Role.admin, Role.client})
    public List<ClientView> getAll(@QueryParam("count") int count) {
        List<Client> clients = clientService.getAll(count);
        return clientPresenter.present(clients);
    }

    @POST
    @Consumes("application/json")
    @Secured({Role.admin})
    public Response create(Credentials credentials, @Context UriInfo uriInfo) {
        URI uri = clientService.create(credentials, uriInfo);
        return Response.created(uri).build();
    }

    @GET
    @Path("/{userId}")
    @Produces("application/json")
    @Secured({Role.admin, Role.client, Role.caregiver})
    public ClientView getById(@PathParam("userId") String userId, @Context SecurityContext context) {
        Client client = clientService.getById(userId, context.getUserPrincipal());
        return clientPresenter.present(client);
    }

    @PUT
    @Path("/{userId}")
    @Consumes("application/json")
    @Secured({Role.admin, Role.client})
    public Response update(@PathParam("userId") String userId, Credentials credentials, @Context SecurityContext context) {
        clientService.update(userId, credentials, context.getUserPrincipal());
        return Response.ok().build();
    }

    @PATCH
    @Path("/{userId}")
    @Consumes("application/json")
    @Secured({Role.admin, Role.client, Role.caregiver})
    public Response patch(@PathParam("userId") String userId, JsonNode patchRequest, @Context SecurityContext context) {
        clientService.patch(userId, patchRequest, context.getUserPrincipal());
        return Response.ok().build();
    }

    @DELETE
    @Path("/{userId}")
    @Secured({Role.admin})
    public void delete(@PathParam("userId") ObjectId id) {
        clientService.deleteById(id);
    }
}