package nl.inholland.imready.app.presenter.client;


public interface ClientHomePresenter {
    void init();
    void invalidateData();
    String getUsername();
    void logout();
}
