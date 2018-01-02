package nl.inholland.imready.app.persistence;


public abstract class UserCache {
    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
