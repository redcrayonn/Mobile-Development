package nl.inholland.imready.app.presenter.client;


import java.util.List;

import nl.inholland.imready.model.blocks.PersonalActivity;
import nl.inholland.imready.model.blocks.PersonalBlock;

public interface ClientHomePresenter {
    void init();
    void refresh();
    String getUsername();
    void logout();
    void getUserInformation();

    List<PersonalActivity> getTodoActivities(List<PersonalBlock> data);
}
