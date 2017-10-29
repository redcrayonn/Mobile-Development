package nl.inholland.projectapi.resource;

import io.swagger.annotations.Api;
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
import nl.inholland.projectapi.model.inputs.Credentials;
import nl.inholland.projectapi.model.Family;
import nl.inholland.projectapi.model.Role;
import nl.inholland.projectapi.model.Secured;
import nl.inholland.projectapi.presentation.FamilyPresenter;
import nl.inholland.projectapi.presentation.model.FamilyView;
import nl.inholland.projectapi.service.FamilyService;
import org.bson.types.ObjectId;

@Api("Family members")
@Path("/api/v1/families")
public class FamilyResource extends BaseResource {

    private final FamilyService familyService;
    private final FamilyPresenter familyPresenter;

    @Inject
    public FamilyResource(
            FamilyService familyService,
            FamilyPresenter familyPresenter) {
        this.familyService = familyService;
        this.familyPresenter = familyPresenter;
    }

    @GET
    @Produces("application/json")
    @Secured({Role.admin})
    public List<FamilyView> getAll(
            @QueryParam("count") int count) {
        List<Family> families = familyService.getAll(count);
        return familyPresenter.present(families);
    }

    @POST
    @Consumes("application/json")
    @Secured({Role.admin})
    public Response create(
            Credentials credentials,
            @Context UriInfo uriInfo) {
        familyService.requireResult(credentials, "Json object in body required");
        URI uri = familyService.create(credentials, uriInfo);
        return Response.created(uri).header("Id", getId(uri)).build();
    }

    @GET
    @Path("/{familyId}")
    @Produces("application/json")
    @Secured({Role.admin, Role.family})
    public FamilyView getById(
            @PathParam("familyId") String familyId,
            @Context SecurityContext context) {
        Family family = familyService.getById(familyId, context.getUserPrincipal());
        return familyPresenter.present(family);
    }

    @PUT
    @Path("/{familyId}")
    @Consumes("application/json")
    @Secured({Role.admin, Role.family})
    public Response update(
            @PathParam("familyId") String familyId,
            Credentials credentials,
            @Context SecurityContext context) {
        familyService.requireResult(credentials, "Json object in body required");
        familyService.update(familyId, credentials, context.getUserPrincipal());
        return Response.ok().build();
    }

    @DELETE
    @Path("/{familyId}")
    @Secured({Role.admin})
    public void delete(
            @PathParam("familyId") ObjectId familyId) {
        familyService.deleteById(familyId);
    }
}
