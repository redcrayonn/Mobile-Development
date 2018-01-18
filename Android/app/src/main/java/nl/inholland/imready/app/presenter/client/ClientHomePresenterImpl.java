package nl.inholland.imready.app.presenter.client;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.nytimes.android.external.store3.base.impl.BarCode;
import com.nytimes.android.external.store3.base.impl.Store;

import java.util.List;

import br.com.zbra.androidlinq.Stream;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import nl.inholland.imready.R;
import nl.inholland.imready.app.ImReadyApplication;
import nl.inholland.imready.app.logic.PreferenceConstants;
import nl.inholland.imready.app.persistence.UserCache;
import nl.inholland.imready.app.view.activity.client.ClientHomeView;
import nl.inholland.imready.model.blocks.PersonalActivity;
import nl.inholland.imready.model.blocks.PersonalBlock;
import nl.inholland.imready.model.blocks.PersonalComponent;
import nl.inholland.imready.model.enums.BlockPartStatus;
import nl.inholland.imready.model.enums.UserRole;
import nl.inholland.imready.model.user.Client;
import nl.inholland.imready.service.model.FutureplanResponse;

import static android.content.Context.MODE_PRIVATE;
import static br.com.zbra.androidlinq.Linq.stream;

public class ClientHomePresenterImpl implements ClientHomePresenter {
    @NonNull
    private final ClientHomeView view;
    private final Store<FutureplanResponse, BarCode> store;
    private final Store<Client, BarCode> clientStore;
    private final Context context;

    public ClientHomePresenterImpl(@NonNull ClientHomeView view, Store<FutureplanResponse, BarCode> store, Store<Client, BarCode> clientStore) {
        this.view = view;
        this.context = view.getContext();
        this.store = store;
        this.clientStore = clientStore;
    }

    @Override
    public void init() {
        fetch(false);
    }

    @Override
    public void refresh() {
        fetch(true);
    }

    private void fetch(boolean fromNetwork) {
        view.showRefreshing();
        String currentUserId = ImReadyApplication.getInstance().getCurrentUserId();

        // cache request param, where type is the key for the cache and key the unique identifier
        BarCode request = new BarCode("future_plan", currentUserId);

        // request data from the futureplan store
        Single<FutureplanResponse> dataRequest;
        if (fromNetwork) {
            dataRequest = store.fetch(request);
        } else {
            dataRequest = store.get(request);
        }

        dataRequest
                // required to pass the data to views (ui changes are required to be on the main thread)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                // callback implementation (onSucces / onFailure)
                .subscribe(new SingleObserver<FutureplanResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        // ignored
                    }

                    @Override
                    public void onSuccess(FutureplanResponse response) {
                        view.setViewData(response.getBlocks());
                        view.stopRefreshing();
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showMessage(context.getString(R.string.personal_block_failed));
                        view.stopRefreshing();
                    }
                });
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
    public void getUserInformation() {
        view.showRefreshing();
        String currentUserId = ImReadyApplication.getInstance().getCurrentUserId();
        BarCode barCode = new BarCode("Client", currentUserId);
        // required to pass the data to views (ui changes are required to be on the main thread)
        clientStore.get(barCode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                // callback implementation (onSucces / onFailure)
                .subscribe(new SingleObserver<Client>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        //ignore
                    }

                    @Override
                    public void onSuccess(Client user) {
                        ImReadyApplication instance = ImReadyApplication.getInstance();
                        UserCache cache = instance.getCache(UserRole.CLIENT);
                        cache.putUserInfo(user);
                        view.updateUserInfo(user);
                        view.stopRefreshing();
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showMessage(context.getString(R.string.user_info_failed));
                        view.stopRefreshing();
                    }
                });
    }

    @Override
    public List<PersonalActivity> getTodoActivities(List<PersonalBlock> data) {
        Stream<PersonalComponent> components = stream(data).selectMany(PersonalBlock::getComponents);
        Stream<PersonalActivity> activities = components.selectMany(PersonalComponent::getActivities);
        return activities.where(activity -> activity.getStatus() == BlockPartStatus.ONGOING).toList();
    }
}
