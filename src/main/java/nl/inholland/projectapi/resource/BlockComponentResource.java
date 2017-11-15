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
import nl.inholland.projectapi.model.Component;
import nl.inholland.projectapi.model.Role;
import nl.inholland.projectapi.model.Secured;
import nl.inholland.projectapi.model.inputs.InputComponent;
import nl.inholland.projectapi.presentation.ComponentPresenter;
import nl.inholland.projectapi.presentation.model.ComponentView;
import nl.inholland.projectapi.service.BlockComponentService;

@Api("General block components")
@Path("/api/v1/blocks/{blockId}/components")
@Secured({Role.admin})
public class BlockComponentResource extends BaseResource{
    private final BlockComponentService service;
    private final ComponentPresenter presenter;

    @Inject
    public BlockComponentResource(
            BlockComponentService service,
            ComponentPresenter presenter) {
        this.service = service;
        this.presenter = presenter;
    }

    @GET
    @Produces("application/json")
    public List<ComponentView> getAllComponents(
            @PathParam("blockId") String blockId,
            @QueryParam("count") int count) {
        List<Component> components = service.getAll(blockId, count);
        return presenter.present(components);
    }

    @POST
    @Consumes("application/json")
    public Response createComponent(
            @PathParam("blockId") String blockId,
            InputComponent input,
            @Context UriInfo uriInfo) {
        service.requireResult(input, "Json object in body required");
        Component component = new Component(input);
        URI uri = service.create(blockId, component, uriInfo);
        return Response.created(uri).header("Id", getId(uri)).build();
    }

    @GET
    @Produces("application/json")
    @Path("/{componentId}")
    public ComponentView getComponentById(
            @PathParam("blockId") String blockId,
            @PathParam("componentId") String componentId) {
        return presenter.present(service.getById(blockId, componentId));
    }

    @PUT
    @Consumes("application/json")
    @Path("/{componentId}")
    public Response updateComponent(
            @PathParam("blockId") String blockId,
            @PathParam("componentId") String componentId,
            InputComponent updatedComponent) {
        service.requireResult(updatedComponent, "Json object required");
        service.update(blockId, componentId, new Component(updatedComponent));
        return Response.ok().build();
    }

    @DELETE
    @Consumes("application/json")
    @Path("/{componentId}")
    public void deleteComponent(
            @PathParam("blockId") String blockId,
            @PathParam("componentId") String componentId) {
        service.delete(blockId, componentId);
    }    
}
