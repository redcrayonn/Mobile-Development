package nl.inholland.imready.app.presenter.client;


import java.util.List;

import nl.inholland.imready.model.blocks.Component;

public interface ClientComponentEditPresenter {
    int getPointsFromComponent(Component component);

    List<String> getActivityNamesFromComponent(Component component);

    void addComponentToFutureplan(Component component);
}
