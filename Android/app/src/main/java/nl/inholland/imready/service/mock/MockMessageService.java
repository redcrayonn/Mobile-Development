package nl.inholland.imready.service.mock;

import nl.inholland.imready.service.rest.MessageService;
import retrofit2.mock.BehaviorDelegate;

class MockMessageService implements MessageService {
    private final BehaviorDelegate<MessageService> delegate;

    public MockMessageService(BehaviorDelegate<MessageService> delegate) {
        this.delegate = delegate;
    }
}
