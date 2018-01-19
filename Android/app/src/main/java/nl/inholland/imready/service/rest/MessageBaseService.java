package nl.inholland.imready.service.rest;

import io.reactivex.Single;
import nl.inholland.imready.model.user.Message;
import nl.inholland.imready.service.model.EmptyResponse;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface MessageBaseService {

    @GET("user/{userId}/chat/{receiverId}")
    Single<ResponseBody> getChat(@Path("userId") String userId, @Path("receiverId") String receiverId);

    @POST("user/{userId}/chat/{receiverId}")
    Call<EmptyResponse> postMessage(@Path("userId") String userId, @Path("receiverId") String receiverId, @Body Message body);
}
