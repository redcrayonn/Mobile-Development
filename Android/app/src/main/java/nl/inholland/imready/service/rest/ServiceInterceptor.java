package nl.inholland.imready.service.rest;

import android.text.TextUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;


public class ServiceInterceptor implements Interceptor {

    private String token;

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (!TextUtils.isEmpty(token)) {
            request = request.newBuilder()
                    .addHeader("Authorization", "Bearer " + token)
                    .build();
        }
        return chain.proceed(request);
    }
}
