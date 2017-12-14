package nl.inholland.imready.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public abstract class BaseClient implements ApiClient {

    protected final String API_URL = "https://imreadyapiv2.azurewebsites.net/api/";

    protected final Retrofit retrofit;

    public BaseClient() {

        // Custom format for dates since backend gives non-timezone'd dates which the default Gson parser needs
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .create();

        // Setup Retrofit
        retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }
}
