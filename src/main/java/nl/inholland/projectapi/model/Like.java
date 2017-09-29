package nl.inholland.projectapi.model;

import java.util.Date;
import org.bson.types.ObjectId;
import org.hibernate.validator.constraints.NotEmpty;
import org.mongodb.morphia.annotations.Reference;

public class Like extends EntityModel {

    @Reference
    private ObjectId senderId;
    @NotEmpty
    private Date datetime;

    public ObjectId getSenderId() {
        return senderId;
    }

    public void setSenderId(ObjectId senderId) {
        this.senderId = senderId;
    }

    public Date getDateTime() {
        return datetime;
    }

    public void setDateTime(Date dateTime) {
        this.datetime = dateTime;
    }

}
