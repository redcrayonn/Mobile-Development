package nl.inholland.imready.app.persistence;

import com.google.gson.reflect.TypeToken;
import com.nytimes.android.external.store3.base.Fetcher;
import com.nytimes.android.external.store3.base.Persister;
import com.nytimes.android.external.store3.base.impl.BarCode;
import com.nytimes.android.external.store3.base.impl.RealStore;
import com.nytimes.android.external.store3.base.impl.Store;
import com.nytimes.android.external.store3.base.impl.StoreBuilder;
import com.nytimes.android.external.store3.middleware.GsonParserFactory;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.annotations.NonNull;
import nl.inholland.imready.app.ImReadyApplication;
import nl.inholland.imready.app.logic.ApiManager;
import nl.inholland.imready.service.BaseClient;
import nl.inholland.imready.service.model.ClientsResponse;
import okhttp3.ResponseBody;
import okio.BufferedSource;

/**
 * Created by Peter on 17/01/2018.
 */

public class ClientsStore extends RealStore<List<ClientsResponse>, BarCode> {
    private ClientsStore() {
        super(null, null);
    }

    public static Store<List<ClientsResponse>, BarCode> create() {
        Persister<BufferedSource, BarCode> persister = ImReadyApplication.getInstance().getPersister();
        BaseClient client = ApiManager.getClient();
        return StoreBuilder.<BarCode, BufferedSource, List<ClientsResponse>>parsedWithKey()
                .fetcher(ClientsStore::getFetcher)
                .persister(persister)
                .networkBeforeStale()
                // tell the datastore to parse data using the provided Gson configuration and turn it into the requested class
                .parser(GsonParserFactory.createSourceParser(client.provideGson(), new TypeToken<List<ClientsResponse>>() {}.getType()))
                // create or open the store
                .open();
    }

    private static @NonNull Single<BufferedSource> getFetcher(BarCode barCode) {
        BaseClient client = ApiManager.getClient();
        return client
                .getCaregiverService()
                .getClients(barCode.getKey())
                .map(ResponseBody::source);
    }
}
