package nl.inholland.imready.app.presenter.shared;


import com.nytimes.android.external.store3.base.impl.BarCode;
import com.nytimes.android.external.store3.base.impl.Store;

import java.util.Date;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import nl.inholland.imready.R;
import nl.inholland.imready.app.view.activity.shared.MessagesView;
import nl.inholland.imready.model.user.Chat;
import nl.inholland.imready.model.user.Message;
import nl.inholland.imready.service.model.EmptyResponse;
import nl.inholland.imready.service.rest.MessageBaseService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MessagesPresenterImpl implements MessagesPresenter, SingleObserver<Chat> {
    private final MessagesView view;
    private final MessageBaseService service;
    private final Store<Chat, BarCode> store;
    private final String userId;
    private final String receiverId;

    private final int maxRetry = 10;
    private int retryCount = 0;

    public MessagesPresenterImpl(MessagesView view, MessageBaseService service, Store<Chat, BarCode> store, String userId, String receiverId) {
        this.view = view;
        this.service = service;

        this.store = store;
        this.userId = userId;
        this.receiverId = receiverId;
    }

    @Override
    public void init() {
        view.showRefreshing();
        fetch(false);
    }

    @Override
    public void refresh() {
        view.showRefreshing();
        fetch(true);
    }

    private void fetch(boolean fromNetwork) {
        BarCode barCode = new BarCode(receiverId, userId);
        Single<Chat> request;
        if (fromNetwork) {
            request = store.fetch(barCode);
        } else {
            request = store.get(barCode);
        }

        request.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this);
    }

    @Override
    public void sendMessage(String text) {
        view.showRefreshing();
        // create the message
        Message message = new Message();
        message.setSent(new Date());
        message.setSenderId(userId);
        message.setMessage(text);
        // update view
        view.addMessage(message);
        // send to server
        retryCount = 0;
        view.showRefreshing();
        service.postMessage(userId, receiverId, message).enqueue(new Callback<EmptyResponse>() {
            @Override
            public void onResponse(Call<EmptyResponse> call, Response<EmptyResponse> response) {
                view.stopRefreshing();
                fetch(true);
            }

            @Override
            public void onFailure(Call<EmptyResponse> call, Throwable t) {
                if (retryCount < maxRetry) {
                    view.showMessage("Couldn't send the messages. retrying...");
                    retryCount++;
                    call.enqueue(this);
                } else {
                    view.showMessage(view.getContext().getString(R.string.unknown_error));
                    view.stopRefreshing();
                }
            }
        });
    }

    @Override
    public void onSubscribe(Disposable d) {
        // ignore
    }

    @Override
    public void onSuccess(Chat chat) {
        List<Message> messages = chat.getMessages();
        view.setViewData(messages);
        view.stopRefreshing();
    }

    @Override
    public void onError(Throwable e) {
        view.showMessage("Couldn't get your messages");
        view.stopRefreshing();
    }
}
