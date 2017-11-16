package nl.inholland.imready.service.mock;

import nl.inholland.imready.service.rest.FamilyService;
import retrofit2.mock.BehaviorDelegate;

class MockFamilyService implements FamilyService {
    private final BehaviorDelegate<FamilyService> delegate;

    public MockFamilyService(BehaviorDelegate<FamilyService> delegate) {
        this.delegate = delegate;
    }
}
