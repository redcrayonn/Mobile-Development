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
import nl.inholland.projectapi.model.Message;
import nl.inholland.projectapi.model.Role;
import nl.inholland.projectapi.model.Secured;
import nl.inholland.projectapi.presentation.MessagePresenter;
import nl.inholland.projectapi.presentation.model.MessageView;
import nl.inholland.projectapi.service.FamilyMessageService;

@Path("/api/v1/families/{familyId}/messages")
public class FamilyMessageResource extends BaseResource {

    private final FamilyMessageService familyMessageService;
    private final MessagePresenter messagePresenter;

    @Inject
    public FamilyMessageResource(FamilyMessageService familyMessageService, MessagePresenter messagePresenter) {
        this.familyMessageService = familyMessageService;
        this.messagePresenter = messagePresenter;
    }

    @GET
    @Produces("application/json")
    @Secured({Role.admin, Role.family})
    public List<MessageView> getAll(@QueryParam("count") int count, @PathParam("familyId") String familyId) {
        List<Message> messages = familyMessageService.getAll(familyId, count);

        if (count != 0) {
            List<Message> reducedList = familyMessageService.reduceList(messages, count);
            return messagePresenter.present(reducedList);
        }
        return messagePresenter.present(messages);
    }

    @POST
    @Consumes("application/json")
    @Secured({Role.admin, Role.client, Role.family})
    public Response create(@PathParam("familyId") String familyId, Message message, @Context UriInfo uriInfo, @Context SecurityContext context) {
        URI uri = familyMessageService.create(message, familyId, context.getUserPrincipal(), uriInfo);
        return Response.created(uri).build();
    }

    @GET
    @Path("/{messageId}")
    @Produces("application/json")
     @Secured({Role.admin, Role.family})
    public MessageView getById(@PathParam("familyId") String familyId, @PathParam("messageId") String messageId, @Context SecurityContext context) {
        Message message = familyMessageService.getById(familyId, messageId);
        return messagePresenter.present(message);
    }
    
    @DELETE
    @Path("/{messageId}")
    @Produces("application/json")
    @Secured({Role.admin, Role.family})
    public void deleteMessage(@PathParam("familyId") String familyId,@PathParam("messageId") String messageId, @Context SecurityContext context) {
       familyMessageService.delete(familyId, messageId);
    }    
}