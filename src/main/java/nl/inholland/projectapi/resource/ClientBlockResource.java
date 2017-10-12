package nl.inholland.projectapi.resource;

import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;
import nl.inholland.projectapi.model.BuildingBlock;
import nl.inholland.projectapi.model.Role;
import nl.inholland.projectapi.model.Secured;
import nl.inholland.projectapi.presentation.ActivityPresenter;
import nl.inholland.projectapi.presentation.BlockPresenter;
import nl.inholland.projectapi.presentation.model.ActivityView;
import nl.inholland.projectapi.presentation.model.BlockView;
import nl.inholland.projectapi.service.ClientBlockService;

@Path("/api/v1/clients/{clientId}/blocks")
@Secured({Role.admin, Role.client, Role.caregiver})
public class ClientBlockResource extends BaseResource {

    private final ClientBlockService clientBlockService;
    private final BlockPresenter blockPresenter;
    private final ActivityPresenter activityPresenter;

    @Inject
    public ClientBlockResource(ClientBlockService clientBlockService, BlockPresenter blockPresenter, ActivityPresenter activityPresenter) {
        this.clientBlockService = clientBlockService;
        this.blockPresenter = blockPresenter;
        this.activityPresenter = activityPresenter;
    }

    @GET
    @Produces("application/json")
    public List<BlockView> getAll(
            @PathParam("clientId") String clientId,
            @QueryParam("count") int count,
            @Context SecurityContext context) {
        List<BuildingBlock> blocks = clientBlockService.getAll(clientId, context.getUserPrincipal(), count);
        return blockPresenter.present(blocks);
    }

    @GET
    @Path("/{blockId}")
    @Produces("application/json")
    public BlockView get(
            @PathParam("clientId") String clientId,
            @PathParam("blockId") String blockId,
            @Context SecurityContext context) {
        return blockPresenter.present(clientBlockService.getById(clientId, blockId, context.getUserPrincipal()));
    }
    
    //new file later
    @GET
    @Path("/{blockId}/activities")
    @Produces("application/json")
    public List<ActivityView> getActivities(
            @PathParam("clientId") String clientId,
            @PathParam("blockId") String blockId,
            @Context SecurityContext context) {
        return activityPresenter.present(clientBlockService.getActivities(clientId, blockId, context.getUserPrincipal()));
    }
}
