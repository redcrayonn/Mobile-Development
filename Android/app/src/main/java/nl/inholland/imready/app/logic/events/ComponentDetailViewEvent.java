package nl.inholland.imready.app.logic.events;


import nl.inholland.imready.model.blocks.Component;

public class ComponentDetailViewEvent {
    private final Component component;

    public ComponentDetailViewEvent(Component component) {
        this.component = component;
    }

    public Component getComponent() {
        return component;
    }
}
