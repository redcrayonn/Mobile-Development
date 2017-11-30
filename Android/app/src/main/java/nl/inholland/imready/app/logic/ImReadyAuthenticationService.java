package nl.inholland.imready.app.logic;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

public class ImReadyAuthenticationService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        ImReadyAuthenticator authenticator = new ImReadyAuthenticator(this);
        return authenticator.getIBinder();
    }
}
