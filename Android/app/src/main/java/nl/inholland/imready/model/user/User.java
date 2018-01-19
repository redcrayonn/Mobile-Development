package nl.inholland.imready.model.user;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import nl.inholland.imready.model.EntityModel;
import nl.inholland.imready.model.enums.UserRole;

public abstract class User extends EntityModel {
    @SerializedName(value = "FirstName", alternate = {"Firstname"})
    private String firstName;
    @SerializedName(value = "LastName", alternate = {"Lastname"})
    private String lastName;
    @SerializedName(value = "Email")
    private String email;
    @SerializedName("Role")
    private UserRole role;
    @SerializedName("Notifications")
    private List<Notification> notifications  = new ArrayList<>();

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

