package nl.inholland.imready.app.presenter.client;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.nytimes.android.external.store3.base.impl.BarCode;
import com.nytimes.android.external.store3.base.impl.Store;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import nl.inholland.imready.R;
import nl.inholland.imready.app.ImReadyApplication;
import nl.inholland.imready.app.logic.PreferenceConstants;
import nl.inholland.imready.app.persistence.UserCache;
import nl.inholland.imready.app.view.view.ClientHomeView;
import nl.inholland.imready.model.enums.UserRole;
import nl.inholland.imready.service.model.FutureplanResponse;

import static android.content.Context.MODE_PRIVATE;

public class ClientHomePresenterImpl implements ClientHomePresenter, SingleObserver<FutureplanResponse> {
    @NonNull
    private final ClientHomeView view;
    private final Store<FutureplanResponse, BarCode> store;
    private final Context context;

    private boolean stale;

    public ClientHomePresenterImpl(@NonNull ClientHomeView view, Store<FutureplanResponse, BarCode> store) {
        this.view = view;
        this.context = view.getContext();
        this.store = store;
    }

    @Override
    public void init() {
        UserCache cache = ImReadyApplication.getInstance().getCache(UserRole.CLIENT);

        // cache request param, where type is the key for the cache and key the unique identifier
        BarCode request = new BarCode("future_plan", cache.getUserId());

        // request data from the futureplan store
        Single<FutureplanResponse> dataRequest;
        if (stale) {
            // get data from network
            dataRequest = store.fetch(request);
        } else {
            // get data from disk and store it in-memory
            // if data is not present it does a network call to retrieve the data from online
            dataRequest = store.get(request);
        }

        dataRequest
                // required to pass the data to views (ui changes are required to be on the main thread)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                // callback implementation (onSucces / onFailure)
                .subscribe(this);
    }

    @Override
    public void invalidateData() {
        stale = true;
    }

    @Override
    public String getUsername() {
        SharedPreferences settings = context.getSharedPreferences(PreferenceConstants.FILE, MODE_PRIVATE);
        return settings.getString(PreferenceConstants.USER_NAME, context.getString(R.string.default_username));
    }

    @Override
    public void logout() {
        SharedPreferences settings = context.getSharedPreferences(PreferenceConstants.FILE, MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.remove(PreferenceConstants.USER_NAME);
        editor.apply();
        view.goToLogin();
    }

    @Override
    public void onSubscribe(Disposable d) {
        //ignore
    }

    @Override
    public void onSuccess(FutureplanResponse response) {
        view.setViewData(response.getBlocks());
    }

    @Override
    public void onError(Throwable e) {
        view.showMessage(context.getString(R.string.personal_block_failed));
    }
}
