package nl.inholland.projectapi.resource;

import java.net.URI;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
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
import nl.inholland.projectapi.service.CaregiverMessageService;

@Path("/api/v1/caregivers/{caregiverId}/messages")
@Secured({Role.admin, Role.caregiver})
public class CaregiverMessageResource extends BaseResource {

    private final CaregiverMessageService caregiverMessageService;
    private final MessagePresenter messagePresenter;

    @Inject
    public CaregiverMessageResource(CaregiverMessageService caregiverMessageService, MessagePresenter messagePresenter) {
        this.caregiverMessageService = caregiverMessageService;
        this.messagePresenter = messagePresenter;
    }

    @GET
    @Produces("application/json")
    public List<MessageView> getAll(
            @QueryParam("count") int count,
            @PathParam("caregiverId") String caregiverId) {

        List<Message> messages = caregiverMessageService.getAll(caregiverId, count);

        if (count != 0) {
            List<Message> reducedList = caregiverMessageService.reduceList(messages, count);
            return messagePresenter.present(reducedList);
        }

        return messagePresenter.present(messages);
    }
    
    @POST
    @Consumes("application/json")
    public Response create(
            @PathParam("caregiverId") String caregiverId,
            Message message,
            @Context UriInfo uriInfo,
            @Context SecurityContext context) {
        URI uri = caregiverMessageService.create(message, caregiverId, context.getUserPrincipal(), uriInfo);
        return Response.created(uri).build();
    }

    @GET
    @Path("/{messageId}")
    @Produces("application/json")
    public MessageView getById(
            @PathParam("caregiverId") String caregiverId,
            @PathParam("messageId") String messageId,
            @Context SecurityContext context) {
        
        Message message = caregiverMessageService.getById(caregiverId, messageId);
        return messagePresenter.present(message);
    }
}
