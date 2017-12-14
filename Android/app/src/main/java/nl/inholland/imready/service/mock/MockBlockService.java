package nl.inholland.imready.service.mock;

import java.util.Iterator;
import java.util.List;

import nl.inholland.imready.model.blocks.Block;
import nl.inholland.imready.service.rest.BlockService;
import retrofit2.Call;
import retrofit2.mock.BehaviorDelegate;

public class MockBlockService implements BlockService {
    private BehaviorDelegate<BlockService> delegate;

    public MockBlockService(BehaviorDelegate<BlockService> delegate) {
        this.delegate = delegate;
    }

    @Override
    public Call<List<Block>> getBlocks() {
        return delegate.returningResponse(MockData.blocks).getBlocks();
    }

    @Override
    public Call<Block> getBlock(String blockId) {
        Iterator<Block> iterator = MockData.blocks.iterator();
        Block block = new Block();
        while (iterator.hasNext()) {
            Block b = iterator.next();
            if (b.getId().equals(blockId)) {
                block = b;
                break;
            }
        }
        return delegate.returningResponse(block).getBlock(blockId);
    }
}
