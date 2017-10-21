package nl.inholland.projectapi.resource;

import io.swagger.annotations.Api;
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
import nl.inholland.projectapi.model.Client;
import nl.inholland.projectapi.model.Message;
import nl.inholland.projectapi.model.Role;
import nl.inholland.projectapi.model.Secured;
import nl.inholland.projectapi.model.inputs.InputMessage;
import nl.inholland.projectapi.presentation.MessagePresenter;
import nl.inholland.projectapi.presentation.model.MessageView;
import nl.inholland.projectapi.service.ClientMessageService;
import nl.inholland.projectapi.service.ClientService;

@Api("Client's messages")
@Path("/api/v1/clients/{clientId}/messages")
public class ClientMessageResource extends BaseResource {

    private final ClientService clientService;
    private final ClientMessageService clientMessageService;
    private final MessagePresenter messagePresenter;

    @Inject
    public ClientMessageResource(
            ClientMessageService clientMessageService,
            ClientService clientService,
            MessagePresenter messagePresenter) {

        this.clientService = clientService;
        this.clientMessageService = clientMessageService;
        this.messagePresenter = messagePresenter;
    }

    @GET
    @Produces("application/json")
    @Secured({Role.admin, Role.client})
    public List<MessageView> getAll(
            @QueryParam("count") int count,
            @PathParam("clientId") String clientId,
            @Context SecurityContext context) {
        Client client = clientService.getById(clientId, context.getUserPrincipal());
        List<Message> messages = clientMessageService.getAll(client, count);
        return messagePresenter.present(messages);
    }

    @POST
    @Consumes("application/json")
    @Secured({Role.admin, Role.client})
    public Response create(
            @PathParam("clientId") String clientId,
            InputMessage input,
            @Context UriInfo uriInfo,
            @Context SecurityContext context) {
        clientService.requireResult(input, "Json object in body required");
        Client client = clientService.getById(clientId, context.getUserPrincipal());
        URI uri = clientMessageService.create(new Message(input), client, uriInfo);
        return Response.created(uri).header("Id", getId(uri)).build();
    }

    @GET
    @Path("/{messageId}")
    @Produces("application/json")
    @Secured({Role.admin, Role.client})
    public MessageView getById(
            @PathParam("clientId") String clientId,
            @PathParam("messageId") String messageId,
            @Context SecurityContext context) {
        Client client = clientService.getById(clientId, context.getUserPrincipal());
        Message message = clientMessageService.getById(client, messageId);
        return messagePresenter.present(message);
    }

    @DELETE
    @Path("/{messageId}")
    @Secured({Role.admin, Role.client})
    public void delete(
            @PathParam("clientId") String clientId,
            @PathParam("messageId") String messageId,
            @Context SecurityContext context) {
        Client client = clientService.getById(clientId, context.getUserPrincipal());
        clientMessageService.delete(client, messageId);
    }

}
