package nl.inholland.imready.app.persistence;


import android.support.annotation.NonNull;

import com.nytimes.android.external.store3.base.Persister;
import com.nytimes.android.external.store3.base.impl.BarCode;
import com.nytimes.android.external.store3.base.impl.RealStore;
import com.nytimes.android.external.store3.base.impl.Store;
import com.nytimes.android.external.store3.base.impl.StoreBuilder;
import com.nytimes.android.external.store3.middleware.GsonParserFactory;

import io.reactivex.Single;
import nl.inholland.imready.app.ImReadyApplication;
import nl.inholland.imready.app.logic.ApiManager;
import nl.inholland.imready.model.user.Chat;
import nl.inholland.imready.service.BaseClient;
import okhttp3.ResponseBody;
import okio.BufferedSource;

public class MessagesStore extends RealStore<Chat, BarCode> {
    private MessagesStore() {
        super(null, null);
    }

    @NonNull
    public static Store<Chat, BarCode> create() {
        Persister<BufferedSource, BarCode> persister = ImReadyApplication.getInstance().getPersister();
        BaseClient client = ApiManager.getClient();
        return StoreBuilder.<BarCode, BufferedSource, Chat>parsedWithKey()
                .fetcher(MessagesStore::getFetcher)
                .persister(persister)
                .networkBeforeStale()
                // tell the datastore to parse data using the provided Gson configuration and turn it into the requested class
                .parser(GsonParserFactory.createSourceParser(client.provideGson(), Chat.class))
                // create or open the store
                .open();
    }

    private static Single<BufferedSource> getFetcher(BarCode barCode) {
        BaseClient client = ApiManager.getClient();
        return client
                .getMessageService()
                .getChat(barCode.getKey(), barCode.getType())
                .map(ResponseBody::source);
    }
}
