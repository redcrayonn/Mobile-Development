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
import nl.inholland.projectapi.model.Family;
import nl.inholland.projectapi.model.Role;
import nl.inholland.projectapi.model.Secured;
import nl.inholland.projectapi.presentation.FamilyPresenter;
import nl.inholland.projectapi.presentation.model.FamilyView;
import nl.inholland.projectapi.service.ClientFamilyService;
import nl.inholland.projectapi.service.ClientService;

@Path("/api/v1/clients/{clientId}/families")
public class ClientFamilyResource extends BaseResource {

    private final ClientFamilyService clientFamilyService;
    private final FamilyPresenter familyPresenter;
    private final ClientService clientService;

    @Inject
    public ClientFamilyResource(ClientFamilyService clientFamilyService, FamilyPresenter familyPresenter, ClientService clientService) {
        this.clientFamilyService = clientFamilyService;
        this.familyPresenter = familyPresenter;
        this.clientService = clientService;
    }

    @GET
    @Secured({Role.admin})
    @Produces("application/json")
    public List<FamilyView> getAll(
            @PathParam("clientId") String clientId,
            @QueryParam("count") int count,
            @Context SecurityContext context) {
        List<Family> family = clientFamilyService.getAll(clientService.getById(clientId, context.getUserPrincipal()), count);
        return familyPresenter.present(family);
    }

    @POST
    @Secured({Role.admin})
    @Consumes("application/json")
    @Produces("application/json")
    public Response create(
            @PathParam("clientId") String clientId,
            Family family,
            @Context UriInfo uriInfo,
            @Context SecurityContext context) {
        URI uri = clientFamilyService.create(clientService.getById(clientId, context.getUserPrincipal()), family, uriInfo);
        return Response.created(uri).build();
    }

    @GET
    @Secured({Role.admin})
    @Produces("application/json")
    @Path("/{familyId}")
    public FamilyView get(
            @PathParam("clientId") String clientId,
            @PathParam("familyId") String familyId,
            @Context SecurityContext context) {
        Family family = clientFamilyService.get(clientService.getById(clientId, context.getUserPrincipal()), familyId);
        return familyPresenter.present(family);
    }

    @PUT
    @Secured({Role.admin})
    @Consumes("application/json")
    @Path("/{familyId}")
    public Response update(
            @PathParam("clientId") String clientId,
            @PathParam("familyId") String familyId,
            Family family,
            @Context SecurityContext context) {
        clientFamilyService.update(clientService.getById(clientId, context.getUserPrincipal()), familyId, family);
        return Response.ok().build();
    }

    @DELETE
    @Secured({Role.admin})
    @Path("/{familyId}")
    public void delete(
            @PathParam("clientId") String clientId,
            @PathParam("familyId") String familyId,
            @Context SecurityContext context) {
        clientFamilyService.delete(clientService.getById(clientId, context.getUserPrincipal()), familyId);
    }
}
