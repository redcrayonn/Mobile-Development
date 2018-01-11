package nl.inholland.imready.app.view.activity.client;


import nl.inholland.imready.app.view.View;
import nl.inholland.imready.model.blocks.PersonalActivity;

public interface ClientBlockDetailsView extends View {
    void showHandInDialog(PersonalActivity activity);
    void showSucces();
}
