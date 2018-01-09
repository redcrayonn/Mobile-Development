package nl.inholland.imready.app.persistence;


import android.support.annotation.NonNull;

import com.google.gson.reflect.TypeToken;
import com.nytimes.android.external.store3.base.Persister;
import com.nytimes.android.external.store3.base.impl.BarCode;
import com.nytimes.android.external.store3.base.impl.RealStore;
import com.nytimes.android.external.store3.base.impl.Store;
import com.nytimes.android.external.store3.base.impl.StoreBuilder;
import com.nytimes.android.external.store3.middleware.GsonParserFactory;

import java.util.List;

import io.reactivex.Single;
import nl.inholland.imready.app.ImReadyApplication;
import nl.inholland.imready.app.logic.ApiManager;
import nl.inholland.imready.model.blocks.Block;
import nl.inholland.imready.service.BaseClient;
import okhttp3.ResponseBody;
import okio.BufferedSource;

public class BlockStore extends RealStore<List<Block>, BarCode> {

    // ignore
    private BlockStore() {
        super(null, null);
    }

    @NonNull
    public static Store<List<Block>, BarCode> create() {
        Persister<BufferedSource, BarCode> persister = ImReadyApplication.getInstance().getPersister();
        BaseClient client = ApiManager.getClient();
        return StoreBuilder.<BarCode, BufferedSource, List<Block>>parsedWithKey()
                .fetcher(BlockStore::getFetcher)
                .persister(persister)
                .networkBeforeStale()
                // tell the datastore to parse data using the provided Gson configuration and turn it into the requested class
                .parser(GsonParserFactory.createSourceParser(client.provideGson(), new TypeToken<List<Block>>() {}.getType()))
                // create or open the store
                .open();
    }

    private static Single<BufferedSource> getFetcher(BarCode barCode) {
        BaseClient client = ApiManager.getClient();
        return client
                .getBlockService()
                .getBlocks()
                .map(ResponseBody::source);
    }
}
