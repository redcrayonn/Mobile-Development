package nl.inholland.imready.service.mock;

import java.util.List;

import nl.inholland.imready.service.model.ClientsResponse;
import nl.inholland.imready.service.rest.CaregiverService;
import retrofit2.Call;
import retrofit2.mock.BehaviorDelegate;

class MockCaregiverService implements CaregiverService {
    private final BehaviorDelegate<CaregiverService> delegate;

    public MockCaregiverService(BehaviorDelegate<CaregiverService> delegate) {
        this.delegate = delegate;
    }

    @Override
    public Call<List<ClientsResponse>> getClients(String caregiverId) {
        return null;
    }
}
