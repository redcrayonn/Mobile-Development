package nl.inholland.imready.app.persistence;

import android.support.annotation.NonNull;

import com.nytimes.android.external.store3.base.Persister;
import com.nytimes.android.external.store3.base.impl.BarCode;
import com.nytimes.android.external.store3.base.impl.MemoryPolicy;
import com.nytimes.android.external.store3.base.impl.RealStore;
import com.nytimes.android.external.store3.base.impl.Store;
import com.nytimes.android.external.store3.base.impl.StoreBuilder;
import com.nytimes.android.external.store3.middleware.GsonParserFactory;

import java.util.concurrent.TimeUnit;

import io.reactivex.Single;
import nl.inholland.imready.app.ImReadyApplication;
import nl.inholland.imready.app.logic.ApiManager;
import nl.inholland.imready.service.BaseClient;
import nl.inholland.imready.service.model.FutureplanResponse;
import okhttp3.ResponseBody;
import okio.BufferedSource;


public class FutureplanStore extends RealStore<FutureplanStore, BarCode> {
    //ignore
    private FutureplanStore() {
        super(null, null);
    }

    public static Store<FutureplanResponse, BarCode> create() {
        Persister<BufferedSource, BarCode> persister = ImReadyApplication.getInstance().getPersister();
        BaseClient client = ApiManager.getClient();
        return StoreBuilder.<BarCode, BufferedSource, FutureplanResponse>parsedWithKey()
                .fetcher(FutureplanStore::getFetcher)
                .persister(persister)
                .memoryPolicy(MemoryPolicy
                        .builder()
                        .setExpireAfterWrite(6)
                        .setExpireAfterTimeUnit(TimeUnit.HOURS)
                        .build()) // keep cache for 6 HOURS after writing
                .networkBeforeStale()
                // tell the datastore to parse data using the provided Gson configuration and turn it into CLASS
                .parser(GsonParserFactory.createSourceParser(client.provideGson(), FutureplanResponse.class))
                // create or open the store
                .open();
    }

    private static @NonNull Single<BufferedSource> getFetcher(BarCode barCode) {
        BaseClient client = ApiManager.getClient();
        return client
                .getClientService()
                .getFuturePlan(barCode.getKey())
                .map(ResponseBody::source);
    }
}
