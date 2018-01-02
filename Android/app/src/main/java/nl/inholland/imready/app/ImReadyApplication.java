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

import io.reactivex.Single;
import nl.inholland.imready.app.logic.ApiManager;
import nl.inholland.imready.app.persistence.ClientCache;
import nl.inholland.imready.app.persistence.UserCache;
import nl.inholland.imready.model.enums.UserRole;
import nl.inholland.imready.service.ApiClient;
import nl.inholland.imready.service.BaseClient;
import nl.inholland.imready.service.model.FutureplanResponse;
import okhttp3.ResponseBody;
import okio.BufferedSource;


public class ImReadyApplication extends Application {

    private static ImReadyApplication instance;

    private HashMap<UserRole, UserCache> userCaches;
    private Store<FutureplanResponse, BarCode> futureplanStore;
    private Persister<BufferedSource, BarCode> persister;
    private BaseClient apiClient;

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
                .parser(GsonParserFactory.createSourceParser(apiClient.provideGson(), FutureplanResponse.class))
                .open();
    }

    private @NonNull Single<BufferedSource> futureplanFecther(BarCode barCode) {
        ApiClient client = ApiManager.getClient();
        return client
                .getClientService()
                .getFuturePlanForPersister(barCode.getKey())
                .map(ResponseBody::source);
    }

    public Store<FutureplanResponse, BarCode> getFutureplanStore() {
        return futureplanStore;
    }
}
