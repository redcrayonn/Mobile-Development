package nl.inholland.projectapi.model;

import java.util.Date;
import nl.inholland.projectapi.model.inputs.InputMessage;
import org.bson.types.ObjectId;
import org.hibernate.validator.constraints.NotEmpty;
import org.mongodb.morphia.annotations.Reference;

public class Message extends EntityModel {

    @Reference
    private ObjectId senderId;
    @Reference
    private ObjectId receiverId;
    @NotEmpty
    private String message;
    @NotEmpty
    private Date datetime;
    @NotEmpty
    private boolean read;

    public Message(InputMessage input) {
        this.receiverId = input.getReceiverId();
        this.message = input.getMessage();
    }

    public Message() {
    }

    public String getSenderId() {
        return senderId.toString();
    }

    public void setSenderId(ObjectId senderId) {
        this.senderId = senderId;
    }

    public String getReceiverId() {
        return receiverId.toString();
    }

    public void setReceiverId(ObjectId receiverId) {
        this.receiverId = receiverId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDateTime() {
        return datetime;
    }

    public void setDateTime(Date dateTime) {
        this.datetime = dateTime;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

}
