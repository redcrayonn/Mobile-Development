package nl.inholland.projectapi.presentation;

import java.util.ArrayList;
import java.util.List;
import nl.inholland.projectapi.model.Message;
import nl.inholland.projectapi.presentation.model.MessageView;

public class MessagePresenter extends BasePresenter
{
    public MessageView present(Message message) {
        MessageView view = new MessageView();
        view.id = message.getId().toHexString();
        view.receiverId = message.getReceiverId().toHexString();
        view.senderId = message.getSenderId().toHexString();
        view.message = message.getMessage();
        view.datetime = message.getDateTime();
        return view;
    }
    
    public List<MessageView> present(List<Message> messages) {
        List<MessageView> views = new ArrayList<>();
        
        for(Message message : messages) {
            MessageView view = new MessageView();
            view.id = message.getId().toHexString();
            view.receiverId = message.getReceiverId().toHexString();
            view.senderId = message.getSenderId().toHexString();
            view.message = message.getMessage();
            view.datetime = message.getDateTime();
            views.add(view);            
        }      
        return views;
    }    
}
