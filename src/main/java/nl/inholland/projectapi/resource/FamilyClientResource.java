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
import nl.inholland.projectapi.model.Family;
import nl.inholland.projectapi.model.Role;
import nl.inholland.projectapi.model.Secured;
import nl.inholland.projectapi.presentation.EntityPresenter;
import nl.inholland.projectapi.presentation.model.BaseView;
import nl.inholland.projectapi.service.FamilyClientService;
import nl.inholland.projectapi.service.FamilyService;

@Api("Family members' clients")
@Path("/api/v1/families/{familyId}/clients")
public class FamilyClientResource extends BaseResource {

    private final FamilyService familyService;
    private final FamilyClientService familyClientService;
    private final EntityPresenter presenter;

    @Inject
    public FamilyClientResource(
            FamilyClientService caregiverClientService,
            FamilyService caregiverService,
            EntityPresenter presenter) {
        this.familyClientService = caregiverClientService;
        this.familyService = caregiverService;
        this.presenter = presenter;
    }

    @GET
    @Produces("application/json")
    @Secured({Role.admin, Role.family})
    public List<BaseView> getAll(
            @PathParam("familyId") String familyId,
            @Context SecurityContext context) {
        Family family = familyService.getById(familyId, context.getUserPrincipal());
        List<Client> clients = familyClientService.getAll(family);
        return presenter.present(clients);
    }
}
