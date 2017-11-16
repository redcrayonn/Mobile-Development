package nl.inholland.imready.service.mock;

import nl.inholland.imready.service.rest.CaregiverService;
import retrofit2.mock.BehaviorDelegate;

class MockCaregiverService implements CaregiverService {
    private final BehaviorDelegate<CaregiverService> delegate;

    public MockCaregiverService(BehaviorDelegate<CaregiverService> delegate) {
        this.delegate = delegate;
    }
}
