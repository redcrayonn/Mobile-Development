package nl.inholland.imready.app.presenter.client;


import android.content.Context;

import com.nytimes.android.external.store3.base.impl.BarCode;
import com.nytimes.android.external.store3.base.impl.Store;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import nl.inholland.imready.R;
import nl.inholland.imready.app.view.activity.client.ClientFutureplanEditView;
import nl.inholland.imready.model.blocks.Block;

public class ClientFutureplanEditPresenterImpl implements ClientFutureplanEditPresenter, SingleObserver<List<Block>> {
    private final BarCode request;
    private final ClientFutureplanEditView view;
    private final Store<List<Block>, BarCode> store;

    public ClientFutureplanEditPresenterImpl(ClientFutureplanEditView view, Store<List<Block>, BarCode> store) {
        this.view = view;
        this.store = store;
        this.request = BarCode.empty();
    }

    @Override
    public void init() {
        store.get(request)
            // required to pass the data to views (ui changes are required to be on the main thread)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            // callback implementation (onSucces / onFailure)
            .subscribe(this);
        view.showRefreshing();
    }

    @Override
    public void refreshData() {
        store.fetch(request)
            // required to pass the data to views (ui changes are required to be on the main thread)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            // callback implementation (onSucces / onFailure)
            .subscribe(this);
        view.showRefreshing();
    }

    @Override
    public void onSubscribe(Disposable d) {
        // ignored
    }

    @Override
    public void onSuccess(List<Block> blocks) {
        view.setData(blocks);
        view.stopRefreshing();
    }

    @Override
    public void onError(Throwable e) {
        Context context = view.getContext();
        String errorMessage = context.getString(R.string.block_failed);
        view.showMessage(errorMessage);
        view.stopRefreshing();
    }
}
