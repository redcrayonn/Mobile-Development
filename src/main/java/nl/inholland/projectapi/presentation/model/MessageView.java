package nl.inholland.projectapi.presentation.model;

import java.util.Date;

public class MessageView extends BaseView {

    public String senderId;
    public String receiverId;
    public String message;
    public Date datetime;
    public boolean read;
}
