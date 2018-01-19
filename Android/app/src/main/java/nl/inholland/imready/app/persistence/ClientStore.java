package nl.inholland.imready.app.persistence;


import com.nytimes.android.external.store3.base.Persister;
import com.nytimes.android.external.store3.base.impl.BarCode;
import com.nytimes.android.external.store3.base.impl.RealStore;
import com.nytimes.android.external.store3.base.impl.Store;
import com.nytimes.android.external.store3.base.impl.StoreBuilder;
import com.nytimes.android.external.store3.middleware.GsonParserFactory;

import io.reactivex.Single;
import io.reactivex.annotations.NonNull;
import nl.inholland.imready.app.ImReadyApplication;
import nl.inholland.imready.app.logic.ApiManager;
import nl.inholland.imready.model.user.Client;
import nl.inholland.imready.service.BaseClient;
import okhttp3.ResponseBody;
import okio.BufferedSource;

public class ClientStore extends RealStore<Client, BarCode> {
    private ClientStore() {
        super(null, null);
    }

    @NonNull
    public static Store<Client, BarCode> create() {
        Persister<BufferedSource, BarCode> persister = ImReadyApplication.getInstance().getPersister();
        BaseClient client = ApiManager.getClient();
        return StoreBuilder.<BarCode, BufferedSource, Client>parsedWithKey()
                .fetcher(ClientStore::getFetcher)
                .persister(persister)
                .networkBeforeStale()
                // tell the datastore to parse data using the provided Gson configuration and turn it into the requested class
                .parser(GsonParserFactory.createSourceParser(client.provideGson(), Client.class))
                // create or open the store
                .open();
    }

    private static Single<BufferedSource> getFetcher(BarCode barCode) {
        BaseClient client = ApiManager.getClient();
        return client
                .getClientService()
                .getClientInfo(barCode.getKey())
                .map(ResponseBody::source);
    }
}
