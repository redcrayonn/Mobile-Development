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
import nl.inholland.projectapi.model.Caregiver;
import nl.inholland.projectapi.model.Client;
import nl.inholland.projectapi.model.Role;
import nl.inholland.projectapi.model.Secured;
import nl.inholland.projectapi.presentation.EntityPresenter;
import nl.inholland.projectapi.presentation.model.BaseView;
import nl.inholland.projectapi.service.CaregiverClientService;
import nl.inholland.projectapi.service.CaregiverService;

@Api("Caregiver's clients")
@Path("/api/v1/caregivers/{caregiverId}/clients")
public class CaregiverClientResource extends BaseResource {

    private final CaregiverService caregiverService;
    private final CaregiverClientService caregiverClientService;
    private final EntityPresenter presenter;

    @Inject
    public CaregiverClientResource(
            CaregiverClientService caregiverClientService,
            CaregiverService caregiverService,
            EntityPresenter presenter) {
        this.caregiverClientService = caregiverClientService;
        this.caregiverService = caregiverService;
        this.presenter = presenter;
    }

    @GET
    @Produces("application/json")
    @Secured({Role.admin, Role.caregiver})
    public List<BaseView> getAll(
            @PathParam("caregiverId") String clientId,
            @Context SecurityContext context) {
        Caregiver caregiver = caregiverService.getById(clientId, context.getUserPrincipal());
        List<Client> clients = caregiverClientService.getAll(caregiver);
        return presenter.present(clients);
    }

}
