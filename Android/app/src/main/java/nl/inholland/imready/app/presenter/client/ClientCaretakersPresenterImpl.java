package nl.inholland.imready.app.presenter.client;


import java.util.ArrayList;
import java.util.List;

import nl.inholland.imready.app.ImReadyApplication;
import nl.inholland.imready.app.persistence.UserCache;
import nl.inholland.imready.app.view.activity.client.ClientCaretakersView;
import nl.inholland.imready.model.enums.UserRole;
import nl.inholland.imready.model.user.Caregiver;
import nl.inholland.imready.model.user.Client;
import nl.inholland.imready.model.user.User;

public class ClientCaretakersPresenterImpl implements ClientCaretakersPresenter {
    private final ClientCaretakersView view;

    public ClientCaretakersPresenterImpl(ClientCaretakersView view) {
        this.view = view;
    }

    @Override
    public void init() {
        List<User> caretakers = new ArrayList<>();

        ImReadyApplication instance = ImReadyApplication.getInstance();
        UserCache cache = instance.getCache(UserRole.CLIENT);
        Client userInfo = (Client) cache.getUserInfo();

        if (userInfo == null) {
            view.showMessage("Unable to get caretakers");
            return;
        }

        Caregiver caregiver = userInfo.getCaregiver();

        if (caregiver != null)
        {
            caretakers.add(caregiver);
        }

        view.setCaretakers(caretakers);
    }
}
