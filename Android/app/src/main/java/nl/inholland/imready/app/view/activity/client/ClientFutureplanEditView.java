package nl.inholland.imready.app.view.activity.client;


import java.util.List;

import nl.inholland.imready.app.view.View;
import nl.inholland.imready.model.blocks.Block;
import nl.inholland.imready.model.blocks.Component;

public interface ClientFutureplanEditView extends View {
    void setData(List<Block> blocks);
    void showRefreshing();
    void stopRefreshing();
    void goToComponentView(Component component);
}
