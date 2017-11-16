package nl.inholland.imready.service.mock;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import nl.inholland.imready.model.blocks.Block;
import nl.inholland.imready.service.rest.BlockService;
import retrofit2.Call;
import retrofit2.mock.BehaviorDelegate;

public class MockBlockService implements BlockService {
    private BehaviorDelegate<BlockService> delegate;

    private List<Block> blocks;

    public MockBlockService(BehaviorDelegate<BlockService> delegate) {
        this.delegate = delegate;
        init();
    }

    private void init() {
        blocks = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            Block block = new Block("Block #" + i);
            blocks.add(block);
        }
    }

    @Override
    public Call<List<Block>> getBlocks() {
        return delegate.returningResponse(blocks).getBlocks();
    }

    @Override
    public Call<Block> getBlock(String blockId) {
        Iterator<Block> iterator = blocks.iterator();
        Block block = null;
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
