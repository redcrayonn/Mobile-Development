package nl.inholland.imready.app.persistence;


import nl.inholland.imready.model.user.User;

public abstract class UserCache {
    private String userId;
    private User user;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void putUserInfo(User user) {
        this.user = user;
    }

    public User getUserInfo() {
        return user;
    }
}
