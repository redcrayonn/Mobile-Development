package nl.inholland.imready.app.logic.events;


import nl.inholland.imready.model.blocks.PersonalBlock;
import nl.inholland.imready.model.blocks.PersonalComponent;

public class BlockDetailViewEvent {

    private final PersonalBlock block;
    private final PersonalComponent component;

    public BlockDetailViewEvent(PersonalBlock block, PersonalComponent component) {
        this.block = block;
        this.component = component;
    }

    public PersonalBlock getBlock() {
        return block;
    }

    public PersonalComponent getComponent() {
        return component;
    }
}
