package nl.inholland.imready.service.rest;

import nl.inholland.imready.service.model.Client;
import nl.inholland.imready.service.model.FutureplanResponse;
import io.reactivex.Single;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ClientService {
    @GET("client/{clientId}/futureplan")
    // returns FuturePlanResponse despite signature
    Single<ResponseBody> getFuturePlan(@Path("clientId") String clientId);

    @POST("client/{clientId}/component/{componentId}")
    Call<Void> enrollComponent(@Path("clientId") String clientId,
                               @Path("componentId") String componentId);

    @GET("client/{clientId}")
    Call<Client> getClient(@Path("clientId") String clientId);

    ;
}
