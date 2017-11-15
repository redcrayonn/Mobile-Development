package nl.inholland.imready.model.blocks.activity;

import java.util.Date;

import nl.inholland.imready.model.EntityModel;

class Likes extends EntityModel {
    private String senderId;
    private Date datetime;

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
}
