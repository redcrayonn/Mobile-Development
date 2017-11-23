package nl.inholland.imready.service.rest;

import java.util.List;

import nl.inholland.imready.model.user.Message;
import nl.inholland.imready.service.model.EmptyResponse;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;

public interface MessageBaseService {
    @GET
    Call<List<Message>> getMessages(String id, Integer count);

    @GET
    Call<Message> getMessage(String id, String messageId);

    @FormUrlEncoded
    Call<EmptyResponse> postMessage(String id, Message body);

    @DELETE
    Call<EmptyResponse> deleteMessage(String id, String messageId);
}
