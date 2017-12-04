package nl.inholland.imready.service.rest;

import java.util.List;

import nl.inholland.imready.model.blocks.Block;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface PersonalBlockService{

    @GET("clients/{clientId}/blocks")
    Call<List<Block>> getBlocks(@Path("blockId") String clientId);

    @POST("clients/{clientId}/blocks")
    Call<List<Block>> createBlock(@Path("clientId") String clientId,
                                  Block block);

    @GET("clients/{clientId}/blocks/{blockId}")
    Call<Block> getBlock(@Path("blockId") String clientId,
                         @Path("blockId") String blockId);

    @DELETE("clients/{clientId}/blocks/{blockId}")
    Call<Block> deleteBlock(@Path("blockId") String clientId,
                            @Path("blockId") String blockId);
}
