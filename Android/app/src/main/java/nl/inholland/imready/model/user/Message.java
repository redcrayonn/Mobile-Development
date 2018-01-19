package nl.inholland.imready.model.user;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.UUID;

import nl.inholland.imready.model.EntityModel;

public class Message extends EntityModel {
    @SerializedName("SenderId")
    private String senderId;
    @SerializedName("Content")
    private String message;
    @SerializedName("SentDate")
    private Date sent;
    @SerializedName("Read")
    private Boolean read;

    public Message() {
        this(UUID.randomUUID().toString(), UUID.randomUUID().toString(), "DEFAULT MESSAGE", new Date(), false);
    }

    public Message(String id, String senderId, String message, Date sent, Boolean read) {
        super(id);
        this.senderId = senderId;
        this.message = message;
        this.sent = sent;
        this.read = read;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getSent() {
        return sent;
    }

    public void setSent(Date sent) {
        this.sent = sent;
    }

    public Boolean isRead() {
        return read;
    }

    public void setRead(Boolean read) {
        this.read = read;
    }
}
