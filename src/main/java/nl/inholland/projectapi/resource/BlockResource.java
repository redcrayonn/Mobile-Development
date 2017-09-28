
package nl.inholland.projectapi.resource;

import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import nl.inholland.projectapi.model.BuildingBlock;
import nl.inholland.projectapi.presentation.BlockPresenter;
import nl.inholland.projectapi.presentation.model.BlockView;
import nl.inholland.projectapi.service.BlockService;

@Path("/blocks")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
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
    public Response getAll() {
        List<BuildingBlock> blocks = blockService.getAllBlocks();
        if (blocks.isEmpty()) {
            throw new WebApplicationException("Niet gevonden", 404);
        }
        List<BlockView> blockView = blockPresenter.present(blocks);
        return Response.ok(blockView, MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("/{blockId}")
    @Produces("application/json")
    public Response getById(@PathParam("blockId") String id) {       
        BuildingBlock block = blockService.getBlockById(id);
        if (block == null) {
            throw new WebApplicationException("Niet gevonden", 404);
        }
        BlockView blockView = blockPresenter.present(block);
        return Response.ok(blockView, MediaType.APPLICATION_JSON).build();
    }
}
