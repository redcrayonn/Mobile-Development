package nl.inholland.imready.model.user;

import java.util.List;

import nl.inholland.imready.model.EntityModel;

public class Chat extends EntityModel {
    private List<Message> messages;

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}
