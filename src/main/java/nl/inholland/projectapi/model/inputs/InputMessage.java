package nl.inholland.projectapi.model.inputs;

import org.bson.types.ObjectId;

public class InputMessage {

    private ObjectId receiverId;
    private String message;

    public InputMessage() {
    }

    public ObjectId getReceiverId() {
        return receiverId;
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

}
