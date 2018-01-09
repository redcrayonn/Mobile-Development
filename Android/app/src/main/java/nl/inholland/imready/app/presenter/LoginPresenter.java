package nl.inholland.imready.app.presenter;


public interface LoginPresenter {
    void validateCredentials(String username, String password);

    String getLastUsedUserName();
}
