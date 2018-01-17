package nl.inholland.imready.app.presenter.shared;


import java.util.List;

import nl.inholland.imready.R;
import nl.inholland.imready.app.ImReadyApplication;
import nl.inholland.imready.app.view.activity.shared.MessagesView;
import nl.inholland.imready.model.user.Message;
import nl.inholland.imready.service.model.EmptyResponse;
import nl.inholland.imready.service.rest.MessageBaseService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MessagesPresenterImpl implements MessagesPresenter {
    private final MessagesView view;
    private final MessageBaseService service;

    private final int maxRetry = 10;
    private int retryCount = 0;

    public MessagesPresenterImpl(MessagesView view, MessageBaseService service) {
        this.view = view;
        this.service = service;
    }

    @Override
    public void init() {
        ImReadyApplication application = ImReadyApplication.getInstance();
        String userId = application.getCurrentUserId();
        view.showRefreshing();
        service.getMessages(userId, 0).enqueue(new Callback<List<Message>>() {
            @Override
            public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
                List<Message> messages = response.body();
                if (response.isSuccessful() && messages != null) {
                    view.setViewData(messages);
                    view.stopRefreshing();
                }
            }

            @Override
            public void onFailure(Call<List<Message>> call, Throwable t) {
                view.showMessage("Couldn't get your messages");
                view.stopRefreshing();
            }
        });
    }

    @Override
    public void sendMessage(String text) {
        ImReadyApplication application = ImReadyApplication.getInstance();
        String userId = application.getCurrentUserId();
        // create the message
        Message message = new Message();
        message.setMessage(text);
        // update view
        view.addMessage(message);
        // send to server
        retryCount = 0;
        service.postMessage(userId, message).enqueue(new Callback<EmptyResponse>() {
            @Override
            public void onResponse(Call<EmptyResponse> call, Response<EmptyResponse> response) {
                view.stopRefreshing();
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
        view.showRefreshing();
    }
}
