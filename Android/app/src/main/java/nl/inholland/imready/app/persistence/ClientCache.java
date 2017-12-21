package nl.inholland.imready.app.persistence;


import java.util.List;

import nl.inholland.imready.model.blocks.PersonalBlock;

public class ClientCache extends UserCache {
    private List<PersonalBlock> personalBlocks;

    public List<PersonalBlock> getPersonalBlocks() {
        return personalBlocks;
    }

    public void setPersonalBlocks(List<PersonalBlock> personalBlocks) {
        this.personalBlocks = personalBlocks;
    }
}
