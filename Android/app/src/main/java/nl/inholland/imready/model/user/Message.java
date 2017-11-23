package nl.inholland.imready.model.user;

import java.util.Date;
import java.util.UUID;

import nl.inholland.imready.model.EntityModel;

public class Message extends EntityModel {
    private String senderId;
    private String receiverId;
    private String message;
    private Date datetime;
    private Boolean read;

    public Message() {
        this(UUID.randomUUID().toString(), UUID.randomUUID().toString(), UUID.randomUUID().toString(), "DEFAULT MESSAGE", new Date(), false);
    }

    public Message(String senderId, String receiverId, String message, Date datetime, Boolean read) {
        this(UUID.randomUUID().toString(), senderId, receiverId, message, datetime, read);
    }

    public Message(String id, String senderId, String receiverId, String message, Date datetime, Boolean read) {
        super(id);
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.message = message;
        this.datetime = datetime;
        this.read = read;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public Boolean isRead() {
        return read;
    }

    public void setRead(Boolean read) {
        this.read = read;
    }
}
