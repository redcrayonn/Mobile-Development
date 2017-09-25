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
public class Like {
    private ObjectId senderId;
    private DateTime dateTime;

    public Like(ObjectId senderId, DateTime dateTime) {
        this.senderId = senderId;
        this.dateTime = dateTime;
    }

    public Like() {
    }

    public ObjectId getSenderId() {
        return senderId;
    }

    public void setSenderId(ObjectId senderId) {
        this.senderId = senderId;
    }

    public DateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(DateTime dateTime) {
        this.dateTime = dateTime;
    }
    
}
