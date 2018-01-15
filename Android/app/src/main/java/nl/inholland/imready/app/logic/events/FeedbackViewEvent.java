package nl.inholland.imready.app.logic.events;

import nl.inholland.imready.model.blocks.PersonalActivity;

/**
 * Created by Peter on 12/01/2018.
 */

public class FeedbackViewEvent {
    public final PersonalActivity activity;

    public FeedbackViewEvent(PersonalActivity activity){
        this.activity = activity;
    }
}
