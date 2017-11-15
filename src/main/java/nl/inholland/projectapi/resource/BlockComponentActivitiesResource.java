package nl.inholland.projectapi.resource;

import io.swagger.annotations.Api;
import java.net.URI;
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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import nl.inholland.projectapi.model.Activity;
import nl.inholland.projectapi.model.Role;
import nl.inholland.projectapi.model.Secured;
import nl.inholland.projectapi.model.inputs.InputActivity;
import nl.inholland.projectapi.presentation.ActivityPresenter;
import nl.inholland.projectapi.presentation.model.ActivityView;
import nl.inholland.projectapi.service.BlockComponentActivityService;

@Api("General block activities")
@Path("/api/v1/blocks/{blockId}/components/{componentId}/activities")
@Secured({Role.admin})
public class BlockComponentActivitiesResource extends BaseResource {

    private final BlockComponentActivityService activityService;
    private final ActivityPresenter activityPresenter;

    @Inject
    public BlockComponentActivitiesResource(
            BlockComponentActivityService activityService,
            ActivityPresenter activityPresenter) {
        this.activityService = activityService;
        this.activityPresenter = activityPresenter;
    }

    @GET
    @Produces("application/json")
    public List<ActivityView> getAllActivities(
            @PathParam("blockId") String blockId,
            @PathParam("componentId") String componentId,
            @QueryParam("count") int count) {
        List<Activity> activities = activityService.getAll(blockId, componentId, count);
        return activityPresenter.present(activities);
    }

    @POST
    @Consumes("application/json")
    public Response createActivity(
            @PathParam("blockId") String blockId,
            @PathParam("componentId") String componentId,
            InputActivity input,
            @Context UriInfo uriInfo) {
        activityService.requireResult(input, "Json object in body required");
        Activity activity = new Activity(input);
        URI uri = activityService.create(blockId, componentId, activity, uriInfo);
        return Response.created(uri).header("Id", getId(uri)).build();
    }

    @GET
    @Produces("application/json")
    @Path("/{activityId}")
    public ActivityView getActivityById(
            @PathParam("blockId") String blockId,
            @PathParam("componentId") String componentId,
            @PathParam("activityId") String activityId) {
        return activityPresenter.present(activityService.getById(blockId, componentId, activityId));
    }

    @PUT
    @Consumes("application/json")
    @Path("/{activityId}")
    public Response updateActivity(
            @PathParam("blockId") String blockId,
            @PathParam("componentId") String componentId,
            @PathParam("activityId") String activityId,
            InputActivity updatedActivity) {
        activityService.requireResult(updatedActivity, "Json object required");
        activityService.update(blockId, componentId, activityId, new Activity(updatedActivity));
        return Response.ok().build();
    }

    @DELETE
    @Consumes("application/json")
    @Path("/{activityId}")
    public void deleteActivity(
            @PathParam("blockId") String blockId,
            @PathParam("componentId") String componentId,
            @PathParam("activityId") String activityId) {
        activityService.delete(blockId, componentId, activityId);
    }
}
