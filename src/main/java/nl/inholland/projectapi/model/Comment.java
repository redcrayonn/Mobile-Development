package nl.inholland.projectapi.model;

import nl.inholland.projectapi.model.inputs.InputComment;
import org.hibernate.validator.constraints.NotEmpty;

public class Comment extends Social {

    @NotEmpty
    private String message;

    public Comment(InputComment comment, String senderId) {
        super(senderId);
        this.message = comment.getMessage();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
