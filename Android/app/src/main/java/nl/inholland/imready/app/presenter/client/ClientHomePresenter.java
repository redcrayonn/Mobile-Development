package nl.inholland.imready.app.presenter.client;


import java.util.List;

import nl.inholland.imready.model.blocks.PersonalActivity;
import nl.inholland.imready.model.blocks.PersonalBlock;

public interface ClientHomePresenter {
    void init();
    void invalidateData();
    String getUsername();
    void logout();

    List<PersonalActivity> getTodoActivities(List<PersonalBlock> data);
}
