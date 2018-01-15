package nl.inholland.imready.app.view.activity.client;


import nl.inholland.imready.app.view.View;
import nl.inholland.imready.model.blocks.Component;

public interface ClientComponentEditView extends View {
    void showConfirmDialog();

    void updateViewData(Component component);

    void goToFutureplan();
}
