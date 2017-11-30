package nl.inholland.imready.service.mock;

import java.util.Date;

import nl.inholland.imready.service.model.ApiKeyResponse;
import nl.inholland.imready.service.rest.ServerAuthenticationService;
import retrofit2.Call;
import retrofit2.mock.BehaviorDelegate;

public class MockServerAuthenticationService implements ServerAuthenticationService {
    private final BehaviorDelegate<ServerAuthenticationService> delegate;

    public MockServerAuthenticationService(BehaviorDelegate<ServerAuthenticationService> delegate) {
        this.delegate = delegate;
    }

    @Override
    public Call<ApiKeyResponse> login(String username, String password) {
        ApiKeyResponse response = new ApiKeyResponse();
        response.setAuthtoken("a0b1c2d3e4f5g6");
        response.setDatetime(new Date());
        return delegate.returningResponse(response).login(username, password);
    }
}
