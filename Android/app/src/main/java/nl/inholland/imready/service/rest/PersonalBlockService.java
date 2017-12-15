package nl.inholland.imready.service.rest;

import java.util.List;

import nl.inholland.imready.model.blocks.PersonalBlock;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PersonalBlockService {

    @GET("clients/{clientId}/blocks")
    Call<List<PersonalBlock>> getBlocks(@Path("blockId") String clientId);
}
