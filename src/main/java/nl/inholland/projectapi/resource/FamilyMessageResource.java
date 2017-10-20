package nl.inholland.projectapi.resource;

import java.net.URI;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import nl.inholland.projectapi.model.Family;
import nl.inholland.projectapi.model.Message;
import nl.inholland.projectapi.model.Role;
import nl.inholland.projectapi.model.Secured;
import nl.inholland.projectapi.presentation.MessagePresenter;
import nl.inholland.projectapi.presentation.model.MessageView;
import nl.inholland.projectapi.service.FamilyMessageService;
import nl.inholland.projectapi.service.FamilyService;

@Path("/api/v1/families/{familyId}/messages")
@Secured({Role.admin, Role.family})
public class FamilyMessageResource extends BaseResource {

    private final FamilyMessageService familyMessageService;
    private final MessagePresenter messagePresenter;
    private final FamilyService familyService;

    @Inject
    public FamilyMessageResource(FamilyMessageService familyMessageService, MessagePresenter messagePresenter, FamilyService familyService) {
        this.familyMessageService = familyMessageService;
        this.messagePresenter = messagePresenter;
        this.familyService = familyService;
    }

    @GET
    @Produces("application/json")
    public List<MessageView> getAll(
            @QueryParam("count") int count,
            @PathParam("familyId") String familyId,
            @Context SecurityContext context) {
        Family family = familyService.getById(familyId, context.getUserPrincipal());
        List<Message> messages = familyMessageService.getAll(family, count);
        return messagePresenter.present(messages);
    }

    @POST
    @Consumes("application/json")
    public Response create(
            @PathParam("familyId") String familyId,
            Message message,
            @Context UriInfo uriInfo,
            @Context SecurityContext context) {
        Family family = familyService.getById(familyId, context.getUserPrincipal());
        URI uri = familyMessageService.create(message, family, uriInfo);
        return Response.created(uri).build();
    }

    @GET
    @Path("/{messageId}")
    @Produces("application/json")
    public MessageView getById(@PathParam("familyId") String familyId, @PathParam("messageId") String messageId, @Context SecurityContext context) {
        Family family = familyService.getById(familyId, context.getUserPrincipal());
        Message message = familyMessageService.getById(family, messageId);
        return messagePresenter.present(message);
    }

    @DELETE
    @Path("/{messageId}")
    @Produces("application/json")
    public void deleteMessage(@PathParam("familyId") String familyId, @PathParam("messageId") String messageId, @Context SecurityContext context) {
        Family family = familyService.getById(familyId, context.getUserPrincipal());
        familyMessageService.delete(family, messageId);
    }
}
