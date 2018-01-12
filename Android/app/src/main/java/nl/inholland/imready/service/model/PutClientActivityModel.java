package nl.inholland.imready.service.model;


import nl.inholland.imready.model.enums.BlockPartStatus;

public class PutClientActivityModel {
    private final BlockPartStatus status;
    private final String content;

    public PutClientActivityModel(BlockPartStatus status, String content) {

        this.status = status;
        this.content = content;
    }
}
