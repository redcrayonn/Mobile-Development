package nl.inholland.imready.service.rest;

import nl.inholland.imready.service.model.ApiKeyResponse;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface AuthenticationService {

    @POST("login")
    @FormUrlEncoded
    Call<ApiKeyResponse> login(@Field("username") String username,
                               @Field("password") String password,
                               @Field("grant_type") String grantType);
}
