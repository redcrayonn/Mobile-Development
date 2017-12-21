package nl.inholland.imready.app;

import android.app.Application;

import java.util.HashMap;

import nl.inholland.imready.app.persistence.ClientCache;
import nl.inholland.imready.app.persistence.UserCache;
import nl.inholland.imready.model.enums.UserRole;


public class ImReadyApplication extends Application {

    private static ImReadyApplication instance;

    private HashMap<UserRole, UserCache> userCaches;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        // init
        userCaches = new HashMap<UserRole, UserCache>() {{
            put(UserRole.CLIENT, new ClientCache());
        }};
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
}
