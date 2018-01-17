package nl.inholland.imready.app.logic.events;

import nl.inholland.imready.model.blocks.PersonalActivity;

/**
 * Created by Peter on 12/01/2018.
 */

public class FeedbackViewEvent {
    public final PersonalActivity activity;
    public final String clientName;
    public final String clientId;

    public FeedbackViewEvent(PersonalActivity activity, String clientName, String clientId){
        this.activity = activity;
        this.clientName = clientName;
        this.clientId = clientId;
    }
}
