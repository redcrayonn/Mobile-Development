package nl.inholland.imready.app.presenter.client;


import nl.inholland.imready.model.blocks.PersonalActivity;
import nl.inholland.imready.model.enums.BlockPartStatus;

public interface ClientBlockDetailsPresenter {
    void putActivity(PersonalActivity activity, String content);
    void putActivity(PersonalActivity activity, BlockPartStatus status);
    void saveActivity(PersonalActivity activity);
}
