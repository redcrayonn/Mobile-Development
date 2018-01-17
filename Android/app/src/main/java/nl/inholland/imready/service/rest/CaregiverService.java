package nl.inholland.imready.service.rest;

import java.util.List;

import io.reactivex.Single;
import nl.inholland.imready.model.blocks.PersonalBlock;
import nl.inholland.imready.service.model.ClientsResponse;
import nl.inholland.imready.service.model.FutureplanResponse;
import nl.inholland.imready.service.model.PutFeedbackModel;
import okhttp3.ResponseBody;
import okio.BufferedSource;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface CaregiverService {
    @GET("caregiver/{caregiverId}/client")
    Single<ResponseBody> getClients(@Path("caregiverId") String caregiverId);

    @GET("client/{clientId}/futureplan")
    Call<FutureplanResponse> getClientPlan(@Path("clientId") String clientId);

    @PUT("caregiver/{caregiverId}/client/{clientId}/activity/{activityId}")
    Call<Void> giveFeedback(@Path("caregiverId") String caregiverid, @Path("clientId") String clientId, @Path("activityId") String activityId, @Body PutFeedbackModel feedbackModel);
}
