package nl.inholland.projectapi.resource;

import io.swagger.annotations.Api;
import java.net.URI;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import nl.inholland.projectapi.model.Client;
import nl.inholland.projectapi.model.Like;
import nl.inholland.projectapi.model.Role;
import nl.inholland.projectapi.model.Secured;
import nl.inholland.projectapi.presentation.LikePresenter;
import nl.inholland.projectapi.presentation.model.LikeView;
import nl.inholland.projectapi.service.ClientBlockComponentActivityLikeService;
import nl.inholland.projectapi.service.ClientService;

@Api("Client's likes on personal activities")
@Path("/api/v1/clients/{clientId}/blocks/{blockId}/components/{componentId}/activities/{activityId}/likes")
@Secured({Role.admin, Role.client, Role.caregiver, Role.family})
public class ClientBlockComponentActivityLikeResource extends BaseResource {

    private final ClientBlockComponentActivityLikeService service;
    private final ClientService clientService;
    private final LikePresenter presenter;

    @Inject
    public ClientBlockComponentActivityLikeResource(
            ClientBlockComponentActivityLikeService service,
            ClientService clientService,
            LikePresenter presenter) {
        this.service = service;
        this.presenter = presenter;
        this.clientService = clientService;
    }

    @GET
    @Produces("application/json")
    public List<LikeView> getAll(
            @PathParam("clientId") String clientId,
            @PathParam("blockId") String blockId,
            @PathParam("componentId") String componentId,
            @PathParam("activityId") String activityId,
            @Context SecurityContext context) {
        Client client = clientService.getById(clientId, context.getUserPrincipal());
        List<Like> likes = service.getAll(client, blockId, componentId, activityId);
        return presenter.present(likes);
    }

    @POST
    @Produces("application/json")
    public Response create(
            @PathParam("clientId") String clientId,
            @PathParam("blockId") String blockId,
            @PathParam("componentId") String componentId,
            @PathParam("activityId") String activityId,
            @Context UriInfo uriInfo,
            @Context SecurityContext context) {
        Client client = clientService.getById(clientId, context.getUserPrincipal());
        URI uri = service.create(client, blockId, componentId, activityId, context.getUserPrincipal(), uriInfo);
        return Response.created(uri).header("Id", getId(uri)).build();
    }

    @GET
    @Path("/{likeId}")
    @Produces("application/json")
    public LikeView get(
            @PathParam("clientId") String clientId,
            @PathParam("blockId") String blockId,
            @PathParam("componentId") String componentId,
            @PathParam("activityId") String activityId,
            @PathParam("likeId") String likeId,
            @Context SecurityContext context) {
        Client client = clientService.getById(clientId, context.getUserPrincipal());
        Like like = service.get(client, blockId, componentId, activityId, likeId);
        return presenter.present(like);
    }

    @DELETE
    @Path("/{likeId}")
    public void delete(
            @PathParam("clientId") String clientId,
            @PathParam("blockId") String blockId,
            @PathParam("componentId") String componentId,
            @PathParam("activityId") String activityId,
            @PathParam("likeId") String likeId,
            @Context SecurityContext context) {
        Client client = clientService.getById(clientId, context.getUserPrincipal());
        service.delete(client, blockId, componentId, activityId, likeId, context.getUserPrincipal());
    }

}
