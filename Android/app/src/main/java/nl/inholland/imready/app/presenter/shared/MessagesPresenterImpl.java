package nl.inholland.imready.app.presenter.shared;


import nl.inholland.imready.app.view.activity.shared.MessagesView;
import nl.inholland.imready.model.user.Message;

public class MessagesPresenterImpl implements MessagesPresenter {
    private final MessagesView view;

    public MessagesPresenterImpl(MessagesView view) {
        this.view = view;
    }

    @Override
    public void sendMessage(Message message) {
        // service

    }
}
