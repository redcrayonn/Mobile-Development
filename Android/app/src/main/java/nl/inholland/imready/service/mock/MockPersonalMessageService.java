package nl.inholland.imready.service.mock;

import java.util.List;

import nl.inholland.imready.model.blocks.Block;
import nl.inholland.imready.model.blocks.PersonalBlock;
import nl.inholland.imready.service.rest.PersonalBlockService;
import retrofit2.Call;
import retrofit2.mock.BehaviorDelegate;

class MockPersonalMessageService implements PersonalBlockService {
    private final BehaviorDelegate<PersonalBlockService> delegate;

    public MockPersonalMessageService(BehaviorDelegate<PersonalBlockService> delegate) {
        this.delegate = delegate;
    }

    @Override
    public Call<List<PersonalBlock>> getBlocks(String clientId) {
        return null;
    }
}
