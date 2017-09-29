package nl.inholland.projectapi.model;

import java.util.List;
import org.hibernate.validator.constraints.NotEmpty;
import org.mongodb.morphia.annotations.Embedded;

public abstract class User extends EntityModel {

    @NotEmpty
    private String name;
    //password todo
    @Embedded
    private List<Message> messages;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}
