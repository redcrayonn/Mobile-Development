package nl.inholland.imready.service.rest;

import io.reactivex.Single;
import nl.inholland.imready.model.blocks.Block;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface BlockService {

    @GET("buildingblock")
    // returns List<Block> despite signature
    Single<ResponseBody> getBlocks();

    @GET("buildingblock/{blockId}")
    Call<Block> getBlock(@Path("blockId") String blockId);
}
