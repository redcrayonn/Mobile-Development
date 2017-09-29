package nl.inholland.projectapi.resource;

import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
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
        List<BuildingBlock> blocks = blockService.getAllBlocks();
        if (blocks.isEmpty()) {
            throw new WebApplicationException("Niet gevonden", 404);
        }
        return blockPresenter.present(blocks);
    }
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response create(BuildingBlock newBlock, @Context UriInfo uriInfo) {
        blockService.create(newBlock);
        UriBuilder builder = uriInfo.getAbsolutePathBuilder();//Get base path (/api/1.0/blocks/)
        builder.path(newBlock.getId());//Add new block ID to base path (/api/1.0/blocks/{id})
        return Response.created(builder.build()).entity(blockPresenter.present(newBlock)).build();//Return 201 response with the new object as json and new location in header
    }
    
    @GET
    @Path("/{blockId}")
    @Produces("application/json")
    public BlockView getById(@PathParam("blockId") String id) {
        BuildingBlock block = blockService.getBlockById(id);
        if (block == null) {
            throw new WebApplicationException("Niet gevonden", 404);
        }
        return blockPresenter.present(block);
    }
    
    @DELETE
    @Path("/{blockId}")
    @Consumes("application/json")
    public void delete(@PathParam("blockId") ObjectId id)
    {
        blockService.deleteById(id);
    }
}
