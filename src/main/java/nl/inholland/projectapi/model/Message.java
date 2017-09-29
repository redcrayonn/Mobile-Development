package nl.inholland.projectapi.model;

import java.util.Date;
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

    public String getSenderId() {
        return senderId.toHexString();
    }

    public void setSenderId(ObjectId senderId) {
        this.senderId = senderId;
    }

    public String getReceiverId() {
        return receiverId.toHexString();
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

}
