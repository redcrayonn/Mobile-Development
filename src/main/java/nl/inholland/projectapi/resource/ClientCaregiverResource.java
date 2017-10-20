package nl.inholland.projectapi.resource;

import java.net.URI;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import nl.inholland.projectapi.model.Caregiver;
import nl.inholland.projectapi.model.Client;
import nl.inholland.projectapi.model.Role;
import nl.inholland.projectapi.model.Secured;
import nl.inholland.projectapi.presentation.CaregiverPresenter;
import nl.inholland.projectapi.presentation.model.CaregiverView;
import nl.inholland.projectapi.service.ClientCaregiverService;
import nl.inholland.projectapi.service.ClientService;

@Path("/api/v1/clients/{clientId}/caregivers")
public class ClientCaregiverResource extends BaseResource {

    private final ClientCaregiverService service;
    private final CaregiverPresenter presenter;
    private final ClientService clientService;

    @Inject
    public ClientCaregiverResource(
            ClientCaregiverService service,
            CaregiverPresenter presenter,
            ClientService clientService) {
        this.service = service;
        this.presenter = presenter;
        this.clientService = clientService;
    }

    @GET
    @Secured({Role.admin})
    @Produces("application/json")
    public List<CaregiverView> getAll(
            @PathParam("clientId") String clientId,
            @QueryParam("count") int count,
            @Context SecurityContext context) {
        Client client = clientService.getById(clientId, context.getUserPrincipal());
        List<Caregiver> caregivers = service.getAll(client, count);
        return presenter.present(caregivers);
    }

    @POST
    @Secured({Role.admin})
    @Consumes("application/json")
    @Produces("application/json")
    public Response create(
            @PathParam("clientId") String clientId,
            Caregiver caregiver,
            @Context UriInfo uriInfo,
            @Context SecurityContext context) {
        Client client = clientService.getById(clientId, context.getUserPrincipal());
        URI uri = service.create(client, caregiver, uriInfo);
        return Response.created(uri).build();
    }

    @GET
    @Secured({Role.admin})
    @Produces("application/json")
    @Path("/{caregiverId}")
    public CaregiverView get(
            @PathParam("clientId") String clientId,
            @PathParam("caregiverId") String caregiverId,
            @Context SecurityContext context) {
        Client client = clientService.getById(clientId, context.getUserPrincipal());
        Caregiver caregiver = service.get(client, caregiverId);
        return presenter.present(caregiver);
    }

    @PUT
    @Secured({Role.admin})
    @Consumes("application/json")
    @Path("/{caregiverId}")
    public Response update(
            @PathParam("clientId") String clientId,
            @PathParam("caregiverId") String caregiverId,
            Caregiver caregiver,
            @Context SecurityContext context) {
        Client client = clientService.getById(clientId, context.getUserPrincipal());
        service.update(client, caregiverId, caregiver);
        return Response.ok().build();
    }

    @DELETE
    @Secured({Role.admin})
    @Path("/{caregiverId}")
    public void delete(
            @PathParam("clientId") String clientId,
            @PathParam("caregiverId") String caregiverId,
            @Context SecurityContext context) {
        Client client = clientService.getById(clientId, context.getUserPrincipal());
        service.delete(client, caregiverId);
    }
}
