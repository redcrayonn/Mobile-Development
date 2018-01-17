package nl.inholland.imready.app.view.activity.shared;


import java.util.List;

import nl.inholland.imready.app.view.View;
import nl.inholland.imready.model.user.Message;

public interface MessagesView extends View {
    void addMessage(Message message);
    void showRefreshing();
    void stopRefreshing();
    void setViewData(List<Message> messages);
}
