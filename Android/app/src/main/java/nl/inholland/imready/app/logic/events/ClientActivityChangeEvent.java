package nl.inholland.imready.app.logic.events;


import nl.inholland.imready.model.blocks.PersonalActivity;

public class ClientActivityChangeEvent {
    public final PersonalActivity activity;

    public ClientActivityChangeEvent(PersonalActivity activity) {
        this.activity = activity;
    }
}
