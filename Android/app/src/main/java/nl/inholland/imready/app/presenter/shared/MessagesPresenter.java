package nl.inholland.imready.app.presenter.shared;


public interface MessagesPresenter {
    void sendMessage(String message);
    void init();
    void refresh();
}
