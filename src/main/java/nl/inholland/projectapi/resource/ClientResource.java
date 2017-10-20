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
    @Secured({Role.admin})
    public List<ClientView> getAll(
            @QueryParam("count") int count) {
        List<Client> clients = clientService.getAll(count);
        return clientPresenter.present(clients);
    }

    @POST
    @Consumes("application/json")
    @Secured({Role.admin})
    public Response create(
            Credentials credentials,
            @Context UriInfo uriInfo) {
        URI uri = clientService.create(credentials, uriInfo);
        return Response.created(uri).build();
    }

    @GET
    @Path("/{clientId}")
    @Produces("application/json")
    @Secured({Role.admin, Role.client, Role.caregiver})
    public ClientView getById(
            @PathParam("clientId") String clientId,
            @Context SecurityContext context) {
        Client client = clientService.getById(clientId, context.getUserPrincipal());
        return clientPresenter.present(client);
    }

    @PUT
    @Path("/{clientId}")
    @Consumes("application/json")
    @Secured({Role.admin, Role.client})
    public Response update(
            @PathParam("clientId") String clientId,
            Credentials credentials,
            @Context SecurityContext context) {
        Client client = clientService.getById(clientId, context.getUserPrincipal());
        clientService.update(client, credentials);
        return Response.ok().build();
    }

    @PATCH
    @Path("/{clientId}")
    @Consumes("application/json")
    @Secured({Role.admin, Role.client, Role.caregiver})
    public Response patch(
            @PathParam("clientId") String clientId,
            JsonNode patchRequest,
            @Context SecurityContext context) {
        Client client = clientService.getById(clientId, context.getUserPrincipal());
        clientService.patch(client, patchRequest);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{clientId}")
    @Secured({Role.admin})
    public void delete(@PathParam("clientId") ObjectId clientId) {
        clientService.deleteById(clientId);
    }
}
