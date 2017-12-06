package inholland.tabletapplication.Services.API;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Peter on 06/12/2017.
 */

public class RestClient {
    private Retrofit retrofit;
     private String API_BASE_URL = "https://api.github.com/";
    //https://futurestud.io/tutorials/retrofit-getting-started-and-android-client

    public RestClient() {
        // Custom format for dates since backend gives non-timezone'd dates which the default Gson parser needs
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .create();

        // Setup Retrofit
        retrofit = new Retrofit.Builder()
                .baseUrl("http://inhollandbackend.azurewebsites.net/api/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    public ClientService createClientService() {
        return retrofit.create(ClientService.class);
    }
}

