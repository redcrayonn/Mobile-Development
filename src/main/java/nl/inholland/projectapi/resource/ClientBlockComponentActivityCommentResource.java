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
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import nl.inholland.projectapi.model.Client;
import nl.inholland.projectapi.model.Comment;
import nl.inholland.projectapi.model.inputs.InputComment;
import nl.inholland.projectapi.model.Role;
import nl.inholland.projectapi.model.Secured;
import nl.inholland.projectapi.presentation.CommentPresenter;
import nl.inholland.projectapi.presentation.model.CommentView;
import nl.inholland.projectapi.service.ClientBlockComponentActivityCommentService;
import nl.inholland.projectapi.service.ClientService;

@Api("Client's comments on personal activities")
@Path("/api/v1/clients/{clientId}/blocks/{blockId}/components/{componentId}/activities/{activityId}/comments")
@Secured({Role.admin, Role.client, Role.caregiver, Role.family})
public class ClientBlockComponentActivityCommentResource extends BaseResource {

    private final ClientBlockComponentActivityCommentService service;
    private final ClientService clientService;
    private final CommentPresenter presenter;

    @Inject
    public ClientBlockComponentActivityCommentResource(
            ClientBlockComponentActivityCommentService service,
            ClientService clientService,
            CommentPresenter presenter) {
        this.service = service;
        this.clientService = clientService;
        this.presenter = presenter;
    }

    @GET
    @Produces("application/json")
    public List<CommentView> getAll(
            @PathParam("clientId") String clientId,
            @PathParam("blockId") String blockId,
            @PathParam("componentId") String componentId,
            @PathParam("activityId") String activityId,
            @Context SecurityContext context) {
        Client client = clientService.getById(clientId, context.getUserPrincipal());
        List<Comment> comments = service.getAll(client, blockId, componentId, activityId);
        return presenter.present(comments);
    }

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response create(
            @PathParam("clientId") String clientId,
            @PathParam("blockId") String blockId,
            @PathParam("componentId") String componentId,
            @PathParam("activityId") String activityId,
            InputComment input,
            @Context UriInfo uriInfo,
            @Context SecurityContext context) {
        clientService.requireResult(input, "Json object in body required");
        Client client = clientService.getById(clientId, context.getUserPrincipal());
        URI uri = service.create(client, blockId, componentId, activityId, input, context.getUserPrincipal(), uriInfo);
        return Response.created(uri).header("Id", getId(uri)).build();
    }

    @GET
    @Path("/{commentId}")
    @Produces("application/json")
    public CommentView get(
            @PathParam("clientId") String clientId,
            @PathParam("blockId") String blockId,
            @PathParam("componentId") String componentId,
            @PathParam("activityId") String activityId,
            @PathParam("commentId") String commentId,
            @Context SecurityContext context) {
        Client client = clientService.getById(clientId, context.getUserPrincipal());
        Comment comment = service.get(client, blockId, componentId, activityId, commentId);
        return presenter.present(comment);
    }

    @PUT
    @Path("/{commentId}")
    @Produces("application/json")
    public Response update(
            @PathParam("clientId") String clientId,
            @PathParam("blockId") String blockId,
            @PathParam("componentId") String componentId,
            @PathParam("activityId") String activityId,
            @PathParam("commentId") String commentId,
            InputComment input,
            @Context SecurityContext context) {
        Client client = clientService.getById(clientId, context.getUserPrincipal());
        service.update(client, blockId, componentId, activityId, commentId, input, context.getUserPrincipal());
        return Response.ok().build();
    }

    @DELETE
    @Path("/{commentId}")
    public void delete(
            @PathParam("clientId") String clientId,
            @PathParam("blockId") String blockId,
            @PathParam("componentId") String componentId,
            @PathParam("activityId") String activityId,
            @PathParam("commentId") String commentId,
            @Context SecurityContext context) {
        Client client = clientService.getById(clientId, context.getUserPrincipal());
        service.delete(client, blockId, componentId, activityId, commentId, context.getUserPrincipal());
    }

}
