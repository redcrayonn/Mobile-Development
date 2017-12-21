package nl.inholland.imready.app.persistence;


public abstract class UserCache {
    private boolean valid = true;

    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public boolean isInvalidated() {
        return valid;
    }

    public void invalidate() {
        valid = false;
    }
}
