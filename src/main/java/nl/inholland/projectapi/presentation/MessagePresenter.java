package nl.inholland.projectapi.presentation;

import java.util.ArrayList;
import java.util.List;
import nl.inholland.projectapi.model.Message;
import nl.inholland.projectapi.presentation.model.MessageView;

public class MessagePresenter extends BasePresenter
{
    public MessageView present(Message message) {
        MessageView view = new MessageView();
        view.receiverId = message.getReceiverId();
        view.senderId = message.getSenderId();
        view.message = message.getMessage();
        view.datetime = message.getDateTime();
        return view;
    }
    
    public List<MessageView> present(List<Message> messages) {
        List<MessageView> views = new ArrayList<>();
        
        for(Message message : messages) {
            views.add(present(message));            
        }      
        return views;
    }    
}
