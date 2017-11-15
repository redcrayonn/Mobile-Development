package nl.inholland.imready.data.service;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface AuthenticationService {

    @POST("")
    @FormUrlEncoded
    Call<Void> login(@Field("username") String username,
                     @Field("password") String password);
}
