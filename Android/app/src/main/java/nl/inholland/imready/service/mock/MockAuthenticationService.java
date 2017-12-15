package nl.inholland.imready.service.mock;

import java.util.Date;

import nl.inholland.imready.service.model.ApiKeyResponse;
import nl.inholland.imready.service.rest.AuthenticationService;
import retrofit2.Call;
import retrofit2.mock.BehaviorDelegate;

public class MockAuthenticationService implements AuthenticationService {
    private final BehaviorDelegate<AuthenticationService> delegate;

    public MockAuthenticationService(BehaviorDelegate<AuthenticationService> delegate) {
        this.delegate = delegate;
    }

    @Override
    public Call<ApiKeyResponse> login(String username, String password, String grantType) {
        ApiKeyResponse response = new ApiKeyResponse();
        response.setAccessToken("a0b1c2d3e4f5g6");
        response.setIssued(new Date());
        grantType = password;
        return delegate.returningResponse(response).login(username, password, grantType);
    }
}
