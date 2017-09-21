/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.inholland.projectapi.resource;

import java.util.List;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import nl.inholland.projectapi.model.Block;
import nl.inholland.projectapi.model.ErrorResponse;
import nl.inholland.projectapi.presentation.BlockPresenter;
import nl.inholland.projectapi.presentation.ErrorPresenter;
import nl.inholland.projectapi.presentation.model.BlockView;
import nl.inholland.projectapi.service.BlockService;

/**
 *
 * @author student
 */
@Path("/blocks")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class BlockResource extends BaseResource {
    private HttpServletResponse response;
    private final BlockService blockService;
    private final BlockPresenter blockPresenter;
    private final ErrorPresenter errorPresenter;
    

    
    @Inject
    public BlockResource(BlockService blockService, BlockPresenter blockPresenter, ErrorPresenter errorPresenter) {
        this.blockService = blockService;
        this.blockPresenter = blockPresenter;
        this.errorPresenter = errorPresenter;
    }
    
    @GET
    @Produces("application/json")
    public Response getAll() {
        List<Block> blocks = blockService.getAllBlocks();
        if(blocks.isEmpty())
        {
            ErrorResponse error = new ErrorResponse(Response.Status.NOT_FOUND.getStatusCode(), Response.Status.NOT_FOUND.getReasonPhrase(), "Sorry team");
            String errorView = errorPresenter.present(error);
            
            return Response.status(Response.Status.NOT_FOUND.getStatusCode()).entity(errorView).build();
        }
        List<BlockView> blockView = blockPresenter.present(blocks);
        return Response.ok(blockView, MediaType.APPLICATION_JSON).build();      
    }
    
    @GET
    @Path("/{blockId}")
    @Produces("application/json")
    public Response getById(@PathParam("blockId") String id) {
        int blockId = 0;
        try{
            blockId = Integer.parseInt(id);
        }
        catch(NumberFormatException e){
            ErrorResponse error = new ErrorResponse(Response.Status.BAD_REQUEST.getStatusCode(), Response.Status.BAD_REQUEST.getReasonPhrase(), "Id moet int zijn");
            String errorView = errorPresenter.present(error);
            return Response.status(Response.Status.BAD_REQUEST.getStatusCode()).entity(errorView).build();
        }
        Block block = blockService.getBlockById(blockId);
        if(block == null)
        {
            ErrorResponse error = new ErrorResponse(Response.Status.NOT_FOUND.getStatusCode(), Response.Status.NOT_FOUND.getReasonPhrase(), "Sorry team");
            String errorView = errorPresenter.present(error);
            
            return Response.status(Response.Status.NOT_FOUND.getStatusCode()).entity(errorView).build();
        }
        String blockView = blockPresenter.present(block);
        return Response.ok(blockView, MediaType.APPLICATION_JSON).build();
    }
}
