package nl.inholland.imready.app;

import android.app.Application;

import com.nytimes.android.external.fs3.SourcePersisterFactory;
import com.nytimes.android.external.store3.base.Persister;
import com.nytimes.android.external.store3.base.impl.BarCode;
import com.nytimes.android.external.store3.base.impl.Store;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import nl.inholland.imready.app.persistence.BlockStore;
import nl.inholland.imready.app.persistence.CaregiverCache;
import nl.inholland.imready.app.persistence.ClientCache;
import nl.inholland.imready.app.persistence.ClientStore;
import nl.inholland.imready.app.persistence.ClientsStore;
import nl.inholland.imready.app.persistence.FutureplanStore;
import nl.inholland.imready.app.persistence.MessagesStore;
import nl.inholland.imready.app.persistence.UserCache;
import nl.inholland.imready.model.blocks.Block;
import nl.inholland.imready.model.enums.UserRole;
import nl.inholland.imready.model.user.Chat;
import nl.inholland.imready.model.user.Client;
import nl.inholland.imready.service.model.ClientsResponse;
import nl.inholland.imready.service.model.FutureplanResponse;
import okio.BufferedSource;


public class ImReadyApplication extends Application {

    private static ImReadyApplication instance;

    private HashMap<UserRole, UserCache> userCaches;
    private Persister<BufferedSource, BarCode> persister;
    private Store<FutureplanResponse, BarCode> futureplanStore;
    private Store<List<Block>, BarCode> blocksStore;
    private Store<List<ClientsResponse>, BarCode> clientsStore;
    private Store<Chat, BarCode> messagesStore;
    private UserRole currentUserRole;
    private Store<Client, BarCode> clientStore;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        // init
        userCaches = new HashMap<UserRole, UserCache>() {{
            put(UserRole.CLIENT, new ClientCache());
            put(UserRole.CAREGIVER, new CaregiverCache());
        }};

        initPersister();
        this.futureplanStore = FutureplanStore.create();
        this.blocksStore = BlockStore.create();
        this.clientsStore = ClientsStore.create();
        this.messagesStore = MessagesStore.create();
        this.clientStore = ClientStore.create();
    }

    @Override
    public void onTerminate() {
        // memory cleanup
        EventBus.getDefault().removeAllStickyEvents();
        super.onTerminate();
    }

    private void initPersister() {
        try {
            File cacheDir = getApplicationContext().getCacheDir();
            persister = SourcePersisterFactory.create(cacheDir);
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public static ImReadyApplication getInstance() {
        return instance;
    }

    public UserCache getCache (UserRole usertype) {
        UserCache cache = userCaches.get(usertype);
        if (cache == null) {
            throw new IllegalArgumentException();
        }
        return cache;
    }

    public Persister<BufferedSource, BarCode> getPersister() {
        return persister;
    }

    public Store<FutureplanResponse, BarCode> getFutureplanStore() {
        return futureplanStore;
    }

    public Store<List<Block>, BarCode> getBlocksStore() {
        return blocksStore;
    }

    public Store<List<ClientsResponse>, BarCode> getClientsStore() {
        return clientsStore;
    }

    public Store<Chat, BarCode> getMessagesStore() {
        return messagesStore;
    }

    public UserRole getCurrentUserRole() {
        if (currentUserRole == null) {
            throw new NullPointerException("A userrole was not set for the application");
        }
        return currentUserRole;
    }

    public void setCurrentUserRole(UserRole currentUserRole) {
        this.currentUserRole = currentUserRole;
    }

    public String getCurrentUserId() {
        UserCache cache = this.getCache(getCurrentUserRole());
        return cache.getUserId();
    }

    public Store<Client, BarCode> getClientStore() {
        return clientStore;
    }
}
