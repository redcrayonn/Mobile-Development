package nl.inholland.imready.service;

import com.google.gson.Gson;

import nl.inholland.imready.app.logic.ApiManager;
import nl.inholland.imready.service.rest.ServiceInterceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public abstract class BaseClient implements ApiClient {

    protected final String API_URL = "https://imreadyapiv2.azurewebsites.net/api/";

    protected final Retrofit retrofit;
    private final Gson gson;
    private final ServiceInterceptor interceptor;

    public BaseClient() {
        // Custom format for dates since backend gives non-timezone'd dates which the default Gson parser needs
        gson = ApiManager.provideGson();

        interceptor = new ServiceInterceptor();
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();

        // Setup Retrofit
        retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public Gson provideGson() {
        return gson;
    }

    public void setToken(String token) {
        interceptor.setToken(token);
    }
}
