package nl.inholland.imready.app.view.view;


import java.util.List;

import nl.inholland.imready.model.blocks.PersonalBlock;

public interface ClientHomeView extends View {
    void setViewData(List<PersonalBlock> data);
    void goToLogin();
    void goToMessages();
    void goToNotifications();
    void goToFamily();
    void goToInfo();
}
