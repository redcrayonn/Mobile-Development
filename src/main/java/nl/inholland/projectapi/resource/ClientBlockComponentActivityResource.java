package nl.inholland.projectapi.resource;

import com.fasterxml.jackson.databind.JsonNode;
import io.dropwizard.jersey.PATCH;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import nl.inholland.projectapi.model.Activity;
import nl.inholland.projectapi.model.Client;
import nl.inholland.projectapi.model.Role;
import nl.inholland.projectapi.model.Secured;
import nl.inholland.projectapi.presentation.PersonalActivityPresenter;
import nl.inholland.projectapi.presentation.model.PersonalActivityView;
import nl.inholland.projectapi.service.ClientBlockComponentActivityService;
import nl.inholland.projectapi.service.ClientService;

@Api("Client's personal activities")
@Path("/api/v1/clients/{clientId}/blocks/{blockId}/components/{componentId}/activities")
public class ClientBlockComponentActivityResource extends BaseResource {

    private final ClientBlockComponentActivityService service;
    private final PersonalActivityPresenter presenter;
    private final ClientService clientService;

    @Inject
    public ClientBlockComponentActivityResource(
            ClientBlockComponentActivityService service,
            PersonalActivityPresenter presenter,
            ClientService clientService) {
        this.service = service;
        this.presenter = presenter;
        this.clientService = clientService;
    }

    @GET
    @Secured({Role.admin, Role.client, Role.caregiver, Role.family})
    @Produces("application/json")
    public List<PersonalActivityView> getActivities(
            @PathParam("clientId") String clientId,
            @PathParam("blockId") String blockId,
            @PathParam("componentId") String componentId,
            @Context SecurityContext context) {
        Client client = clientService.getById(clientId, context.getUserPrincipal());
        List<Activity> activities = service.getActivities(client, blockId, componentId);
        return presenter.present(activities);
    }

    @GET
    @Path("/{activityId}")
    @Secured({Role.admin, Role.client, Role.caregiver, Role.family})
    @Produces("application/json")
    public PersonalActivityView getActivity(
            @PathParam("clientId") String clientId,
            @PathParam("blockId") String blockId,
            @PathParam("componentId") String componentId,
            @PathParam("activityId") String activityId,
            @Context SecurityContext context) {
        Client client = clientService.getById(clientId, context.getUserPrincipal());
        Activity activity = service.getActivity(client, blockId, componentId, activityId);
        return presenter.present(activity);
    }

    @PATCH
    @ApiOperation("http://jsonpatch.com")
    @Secured({Role.admin, Role.client, Role.caregiver})
    @Path("/{activityId}")
    public Response patch(
            @PathParam("clientId") String clientId,
            @PathParam("blockId") String blockId,
            @PathParam("componentId") String componentId,
            @PathParam("activityId") String activityId,
            JsonNode patchRequest,
            @Context SecurityContext context) {
        clientService.requireResult(patchRequest, "Json object in body required");
        Client client = clientService.getById(clientId, context.getUserPrincipal());
        service.patch(client, blockId, componentId, activityId, patchRequest);
        return Response.ok().build();
    }
}
