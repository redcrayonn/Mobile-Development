package nl.inholland.imready.model.user;

import java.util.List;

import nl.inholland.imready.model.EntityModel;

public abstract class User extends EntityModel {
    private String firstName;
    private String lastName;
    private UserRole role;
    private List<Notification> notifications;

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
}

