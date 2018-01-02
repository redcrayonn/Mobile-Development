package nl.inholland.imready.app.logic.events;


import java.util.List;

import nl.inholland.imready.model.blocks.PersonalBlock;

public class PersonalBlockLoadedEvent {
    public final List<PersonalBlock> blocks;

    public PersonalBlockLoadedEvent(List<PersonalBlock> blocks) {
        this.blocks = blocks;
    }
}
