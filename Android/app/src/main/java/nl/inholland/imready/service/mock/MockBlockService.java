package nl.inholland.imready.service.mock;

import java.util.Iterator;

import io.reactivex.Single;
import nl.inholland.imready.model.blocks.Block;
import nl.inholland.imready.service.rest.BlockService;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.mock.BehaviorDelegate;

public class MockBlockService implements BlockService {
    private BehaviorDelegate<BlockService> delegate;

    public MockBlockService(BehaviorDelegate<BlockService> delegate) {
        this.delegate = delegate;
    }

    @Override
    public Single<ResponseBody> getBlocks() {
        return delegate.returningResponse(MockData.blocks).getBlocks();
    }

    @Override
    public Call<Block> getBlock(String blockId) {
        Iterator<Block> iterator = MockData.blocks.iterator();
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
