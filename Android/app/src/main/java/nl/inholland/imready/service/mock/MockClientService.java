package nl.inholland.imready.service.mock;

import nl.inholland.imready.service.rest.ClientService;
import retrofit2.mock.BehaviorDelegate;

class MockClientService implements ClientService {
    private final BehaviorDelegate<ClientService> delegate;

    public MockClientService(BehaviorDelegate<ClientService> delegate) {
        this.delegate = delegate;
    }
}
