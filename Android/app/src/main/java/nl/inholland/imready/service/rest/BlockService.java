package nl.inholland.imready.service.rest;

import java.util.List;

import nl.inholland.imready.model.blocks.Block;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface BlockService {

    @GET("blocks")
    Call<List<Block>> getBlocks();

    @GET("blocks/{blockId}")
    Call<Block> getBlock(@Path("blockId") String blockId);
}
