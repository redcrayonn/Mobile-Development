package nl.inholland.projectapi.model;

import java.util.Date;
import nl.inholland.projectapi.model.inputs.InputComment;
import org.bson.types.ObjectId;
import org.hibernate.validator.constraints.NotEmpty;
import org.mongodb.morphia.annotations.Reference;

public class Comment extends EntityModel {

    @Reference
    private ObjectId senderId;
    @NotEmpty
    private String message;
    @NotEmpty
    private Date datetime;

    public Comment(InputComment comment) {
        this.message = comment.getMessage();
    }

    public Comment() {
    }
  
    public String getSenderId() {
        return senderId.toString();
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

    public Date getDateTime() {
        return datetime;
    }

    public void setDateTime(Date dateTime) {
        this.datetime = dateTime;
    }

}
