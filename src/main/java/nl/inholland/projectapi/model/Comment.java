/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.inholland.projectapi.model;

import org.bson.types.ObjectId;
import org.joda.time.DateTime;

/**
 *
 * @author Stefan
 */
public class Comment {
    private ObjectId senderId;
    private String message;
    private DateTime dateTime;

    public Comment() {
    }

    public Comment(ObjectId senderId, String message, DateTime dateTime) {
        this.senderId = senderId;
        this.message = message;
        this.dateTime = dateTime;
    }

    public ObjectId getSenderId() {
        return senderId;
    }

    public void setSenderId(ObjectId senderId) {
        this.senderId = senderId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(DateTime dateTime) {
        this.dateTime = dateTime;
    }
    
}
