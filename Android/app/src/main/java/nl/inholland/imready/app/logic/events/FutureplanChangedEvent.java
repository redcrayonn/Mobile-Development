package nl.inholland.imready.app.logic.events;


public class FutureplanChangedEvent {
    public final String componentId;

    public FutureplanChangedEvent(String componentId) {

        this.componentId = componentId;
    }
}
