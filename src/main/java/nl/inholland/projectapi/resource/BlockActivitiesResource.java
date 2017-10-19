package nl.inholland.projectapi.resource;

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
import nl.inholland.projectapi.model.Activity;
import nl.inholland.projectapi.model.BuildingBlock;
import nl.inholland.projectapi.model.Role;
import nl.inholland.projectapi.model.Secured;
import nl.inholland.projectapi.presentation.ActivityPresenter;
import nl.inholland.projectapi.presentation.model.ActivityView;
import nl.inholland.projectapi.service.BlockActivityService;
import nl.inholland.projectapi.service.BlockService;

@Path("/api/v1/blocks/{blockId}/activities")
@Secured({Role.admin})
public class BlockActivitiesResource extends BaseResource {

    private final BlockService blockService;
    private final BlockActivityService activityService;
    private final ActivityPresenter activityPresenter;

    @Inject
    public BlockActivitiesResource(
            BlockService blockService,
            BlockActivityService activityService,
            ActivityPresenter activityPresenter) {
        this.blockService = blockService;
        this.activityService = activityService;
        this.activityPresenter = activityPresenter;
    }

    @GET
    @Produces("application/json")
    public List<ActivityView> getAllActivities(
            @PathParam("blockId") String blockId,
            @Context SecurityContext context) {
        return activityPresenter.present(blockService.getById(blockId).getActivities());
    }

    @POST
    @Consumes("application/json")
    public Response createActivity(
            @PathParam("blockId") String blockId,
            Activity activity,
            @Context UriInfo uriInfo,
            @Context SecurityContext context) {
        return Response.created(activityService.create(blockService.getById(blockId), activity, uriInfo)).build();
    }

    @GET
    @Produces("application/json")
    @Path("/{activityId}")
    public ActivityView getActivityById(
            @PathParam("blockId") String blockId,
            @PathParam("activityId") String activityId,
            @Context SecurityContext context) {
        return activityPresenter.present(activityService.getById(blockService.getById(blockId), activityId));
    }

    @PUT
    @Consumes("application/json")
    @Path("/{activityId}")
    public Response updateActivity(
            @PathParam("blockId") String blockId,
            @PathParam("activityId") String activityId,
            Activity updatedActivity,
            @Context UriInfo uriInfo,
            @Context SecurityContext context) {
        BuildingBlock blockFound = blockService.getById(blockId);
        activityService.update(activityService.getById(blockFound, activityId), updatedActivity, blockFound);
        return Response.ok().build();
    }

    @DELETE
    @Consumes("application/json")
    @Path("/{activityId}")
    public void deleteActivity(
            @PathParam("blockId") String blockId,
            @PathParam("activityId") String activityId,
            @Context UriInfo uriInfo,
            @Context SecurityContext context) {
        BuildingBlock blockFound = blockService.getById(blockId);
        activityService.delete(activityService.getById(blockFound, activityId), blockFound);
    }
}
