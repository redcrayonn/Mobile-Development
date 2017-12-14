package nl.inholland.imready.service.rest;

import java.util.List;

import nl.inholland.imready.model.user.Message;
import nl.inholland.imready.service.model.EmptyResponse;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MessageClientService extends MessageBaseService {
    @Override
    @GET("clients/{clientId}/messages")
    Call<List<Message>> getMessages(@Path("clientId") String clientId,
                                    @Query("count") Integer count);

    @Override
    @GET("clients/{clientId}/messages/{messageId}")
    Call<Message> getMessage(@Path("clientId") String clientId,
                             @Path("messageId") String messageId);

    @Override
    @GET("clients/{clientId}/messages")
    Call<EmptyResponse> postMessage(@Path("clientId") String clientId,
                                    @Field("body") Message body);

    @Override
    @GET("clients/{clientId}/messages")
    Call<EmptyResponse> deleteMessage(@Path("clientId") String clientId,
                                      @Path("messageId") String messageId);
}
