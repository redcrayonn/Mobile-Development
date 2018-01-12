package nl.inholland.imready.app.presenter.client;


import nl.inholland.imready.app.view.activity.client.ClientCaretakersView;

public class ClientCaretakersPresenterImpl implements ClientCaretakersPresenter {
    private final ClientCaretakersView view;

    public ClientCaretakersPresenterImpl(ClientCaretakersView view) {
        this.view = view;
    }
}
