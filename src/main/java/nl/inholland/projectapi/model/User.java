/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.inholland.projectapi.model;

import java.util.List;
import org.bson.types.ObjectId;

/**
 *
 * @author Stefan
 */
public abstract class User{
    private ObjectId id;
    private String username;
    //password todo
    private List<Message> messages;

    public User() {
    }

    public User(ObjectId id, String username, List<Message> messages) {
        this.id = id;
        this.username = username;
        this.messages = messages;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}
