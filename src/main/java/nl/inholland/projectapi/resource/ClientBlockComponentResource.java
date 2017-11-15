package nl.inholland.projectapi.resource;

import io.swagger.annotations.Api;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;
import nl.inholland.projectapi.model.Client;
import nl.inholland.projectapi.model.Component;
import nl.inholland.projectapi.model.Role;
import nl.inholland.projectapi.model.Secured;
import nl.inholland.projectapi.presentation.PersonalComponentPresenter;
import nl.inholland.projectapi.presentation.model.PersonalComponentView;
import nl.inholland.projectapi.service.ClientBlockComponentService;
import nl.inholland.projectapi.service.ClientService;

@Api("Client's personal components")
@Path("/api/v1/clients/{clientId}/blocks/{blockId}/components")
public class ClientBlockComponentResource extends BaseResource {

    private final ClientBlockComponentService service;
    private final PersonalComponentPresenter presenter;
    private final ClientService clientService;

    @Inject
    public ClientBlockComponentResource(
            ClientBlockComponentService service,
            PersonalComponentPresenter presenter,
            ClientService clientService) {
        this.service = service;
        this.presenter = presenter;
        this.clientService = clientService;
    }

    @GET
    @Secured({Role.admin, Role.client, Role.caregiver, Role.family})
    @Produces("application/json")
    public List<PersonalComponentView> getComponents(
            @PathParam("clientId") String clientId,
            @PathParam("blockId") String blockId,
            @Context SecurityContext context) {
        Client client = clientService.getById(clientId, context.getUserPrincipal());
        List<Component> components = service.getComponents(client, blockId);
        return presenter.present(components);
    }

    @GET
    @Path("/{componentId}")
    @Secured({Role.admin, Role.client, Role.caregiver, Role.family})
    @Produces("application/json")
    public PersonalComponentView getComponent(
            @PathParam("clientId") String clientId,
            @PathParam("blockId") String blockId,
            @PathParam("componentId") String componentId,
            @Context SecurityContext context) {
        Client client = clientService.getById(clientId, context.getUserPrincipal());
        Component component = service.getComponent(client, blockId, componentId);
        return presenter.present(component);
    }
}
