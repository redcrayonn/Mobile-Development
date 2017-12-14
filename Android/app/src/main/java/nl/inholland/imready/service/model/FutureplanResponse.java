package nl.inholland.imready.service.model;

import java.util.List;

import nl.inholland.imready.model.blocks.PersonalBlock;

public class FutureplanResponse {
    private List<PersonalBlock> blocks;

    public List<PersonalBlock> getBlocks() {
        return blocks;
    }

    public void setBlocks(List<PersonalBlock> blocks) {
        this.blocks = blocks;
    }
}
