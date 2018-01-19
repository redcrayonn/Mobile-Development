package nl.inholland.imready.service.mock;

import io.reactivex.Single;
import nl.inholland.imready.service.model.Client;
import nl.inholland.imready.service.model.EmptyResponse;
import nl.inholland.imready.service.model.PutClientActivityModel;
import nl.inholland.imready.service.rest.ClientService;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.mock.BehaviorDelegate;

class MockClientService implements ClientService {
    private final BehaviorDelegate<ClientService> delegate;

    public MockClientService(BehaviorDelegate<ClientService> delegate) {
        this.delegate = delegate;
    }

    @Override
    public Single<ResponseBody> getFuturePlan(String clientId) {
        return delegate.returningResponse(new EmptyResponse()).getFuturePlan(clientId);
    }

    @Override
    public Call<Void> enrollComponent(String clientId, String componentId) {
        return delegate.returningResponse(null).enrollComponent(clientId, componentId);
    }

    @Override
    public Single<ResponseBody> getClientInfo(String clientId) {
        return delegate.returningResponse(new EmptyResponse()).getClientInfo(clientId);
    }

    @Override
    public Call<Client> getClient(String clientId) {
        return null;
    }

    @Override
    public Call<Void> putActivity(String clientId, String activityId, PutClientActivityModel model) {
        return delegate.returningResponse(null).putActivity(clientId, activityId, model);
    }
}
