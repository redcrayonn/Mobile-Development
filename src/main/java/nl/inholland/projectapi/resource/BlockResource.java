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
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import nl.inholland.projectapi.model.BuildingBlock;
import nl.inholland.projectapi.presentation.BlockPresenter;
import nl.inholland.projectapi.presentation.model.BlockView;
import nl.inholland.projectapi.service.BlockService;
import org.bson.types.ObjectId;

@Path("/api/v1/blocks")
public class BlockResource extends BaseResource {

    private final BlockService blockService;
    private final BlockPresenter blockPresenter;

    @Inject
    public BlockResource(BlockService blockService, BlockPresenter blockPresenter) {
        this.blockService = blockService;
        this.blockPresenter = blockPresenter;
    }

    @GET
    @Produces("application/json")
    public List<BlockView> getAll() {
        List<BuildingBlock> blocks = blockService.getAll();
        return blockPresenter.present(blocks);
    }

    @POST
    @Consumes("application/json")
    public Response create(BuildingBlock newBlock, @Context UriInfo uriInfo) {
        UriBuilder builder = blockService.create(newBlock, uriInfo);
        return Response.created(builder.build()).build();//Return 201 response with the new location in header
    }

    @PUT
    @Path("/{blockId}")
    @Consumes("application/json")
    public Response put(@PathParam("blockId") String id, BuildingBlock newBlock) {
        blockService.update(id, newBlock);
        return Response.ok().build();
    }
    
    @GET
    @Path("/{blockId}")
    @Produces("application/json")
    public BlockView getById(@PathParam("blockId") String id) {
        BuildingBlock block = blockService.getById(id);
        return blockPresenter.present(block);
    }

    @DELETE
    @Path("/{blockId}")
    public void delete(@PathParam("blockId") ObjectId id) {
        blockService.deleteById(id);
    }
}
