package nl.inholland.imready.app;

import android.app.Application;
import android.support.annotation.NonNull;

import com.nytimes.android.external.fs3.SourcePersisterFactory;
import com.nytimes.android.external.store3.base.Persister;
import com.nytimes.android.external.store3.base.impl.BarCode;
import com.nytimes.android.external.store3.base.impl.Store;
import com.nytimes.android.external.store3.base.impl.StoreBuilder;
import com.nytimes.android.external.store3.middleware.GsonParserFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Single;
import nl.inholland.imready.app.logic.ApiManager;
import nl.inholland.imready.app.persistence.ClientCache;
import nl.inholland.imready.app.persistence.UserCache;
import nl.inholland.imready.model.blocks.Block;
import nl.inholland.imready.model.enums.UserRole;
import nl.inholland.imready.service.BaseClient;
import nl.inholland.imready.service.model.FutureplanResponse;
import okhttp3.ResponseBody;
import okio.BufferedSource;


public class ImReadyApplication extends Application {

    private static ImReadyApplication instance;

    private HashMap<UserRole, UserCache> userCaches;
    private Persister<BufferedSource, BarCode> persister;
    private BaseClient apiClient;
    private Store<FutureplanResponse, BarCode> futureplanStore;
    private Store<List<Block>, BarCode> blocksStore;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        // init
        userCaches = new HashMap<UserRole, UserCache>() {{
            put(UserRole.CLIENT, new ClientCache());
        }};

        apiClient = (BaseClient) ApiManager.getClient();

        initPersister();
        this.futureplanStore = providePersistedFutureplanStore();
        this.blocksStore = providePersistedBlocksStore();
    }

    public UserCache getCache (UserRole usertype) {
        UserCache cache = userCaches.get(usertype);
        if (cache == null) {
            throw new IllegalArgumentException();
        }
        return cache;
    }

    public static ImReadyApplication getInstance() {
        return instance;
    }


    private void initPersister() {
        try {
            persister = newPersister();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    private Persister<BufferedSource, BarCode> newPersister() throws IOException {
        return SourcePersisterFactory.create(getApplicationContext().getCacheDir());
    }

    @NonNull
    private Store<FutureplanResponse, BarCode> providePersistedFutureplanStore(){
        return StoreBuilder.<BarCode, BufferedSource, FutureplanResponse>parsedWithKey()
                .fetcher(this::futureplanFecther)
                .persister(persister)
                // tell the datastore to parse data using the provided Gson configuration and turn it into CLASS
                .parser(GsonParserFactory.createSourceParser(apiClient.provideGson(), FutureplanResponse.class))
                // create or open the store
                .open();
    }

    private @NonNull Single<BufferedSource> futureplanFecther(BarCode barCode) {
        return apiClient
                .getClientService()
                .getFuturePlan(barCode.getKey())
                .map(ResponseBody::source);
    }

    public Store<FutureplanResponse, BarCode> getFutureplanStore() {
        return futureplanStore;
    }

    @NonNull
    private Store<List<Block>, BarCode> providePersistedBlocksStore(){
        return StoreBuilder.<BarCode, BufferedSource, List<Block>>parsedWithKey()
                .fetcher(this::blocksFecther)
                .persister(persister)
                // tell the datastore to parse data using the provided Gson configuration and turn it into CLASS
                .parser(GsonParserFactory.createSourceParser(apiClient.provideGson(), List.class))
                // create or open the store
                .open();
    }

    private @NonNull Single<BufferedSource> blocksFecther(BarCode barCode) {
        return apiClient
                .getBlockService()
                .getBlocks()
                .map(ResponseBody::source);
    }

    public Store<List<Block>, BarCode> getBlocksStore() {
        return blocksStore;
    }
}
