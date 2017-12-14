package nl.inholland.imready.service.mock;

import nl.inholland.imready.service.rest.MessageBaseService;
import retrofit2.mock.BehaviorDelegate;

abstract class MockMessageBaseService extends MockBaseService {
    protected final BehaviorDelegate<MessageBaseService> delegate;

    public MockMessageBaseService(BehaviorDelegate<MessageBaseService> delegate) {
        this.delegate = delegate;
    }
}
