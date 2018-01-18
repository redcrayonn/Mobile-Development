package nl.inholland.imready.app.view.activity.client;


import java.util.List;

import nl.inholland.imready.app.view.View;
import nl.inholland.imready.model.blocks.PersonalBlock;
import nl.inholland.imready.model.user.User;

public interface ClientHomeView extends View {
    void setViewData(List<PersonalBlock> data);
    void goToLogin();
    void goToMessages();
    void goToNotifications();
    void goToFamily();
    void goToInfo();
    void goToBlockInfo(PersonalBlock block);
    void goToEditFutureplan();

    void updateUserInfo(User user);

    void showRefreshing();
    void stopRefreshing();
}
