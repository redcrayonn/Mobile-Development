package nl.inholland.imready.service.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Peter on 17/12/2017.
 */

public class ClientsResponse {
    @SerializedName("Id")
    private String id;

    @SerializedName("Name")
    private String name;

    @SerializedName("NotificationCount")
    private int notificationCount;

    public String getId() {return id;}

    public String getName() {return name;}

    public int getNotificationCount() {return notificationCount;}

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNotificationCount(int notificationCount) {
        this.notificationCount = notificationCount;
    }
}
