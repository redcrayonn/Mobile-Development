package inholland.tabletapplication.Models;

/**
 * Created by Peter on 06/12/2017.
 */

public class Like {
    private String id;
    private String senderId;
    private String dateTime;

    public String getSenderId(){
        return senderId;
    }

    public String getDateTime(){
        return dateTime;
    }
}
