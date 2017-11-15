package nl.inholland.imready.model.blocks.activity;

import java.util.Date;

import nl.inholland.imready.model.EntityModel;

public class Comments extends EntityModel {
    private String senderId;
    private Date datetime;
    private String message;

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
