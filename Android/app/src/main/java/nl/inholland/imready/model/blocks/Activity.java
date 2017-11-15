package nl.inholland.imready.model.blocks;

import nl.inholland.imready.model.NamedEntityModel;

public class Activity extends NamedEntityModel {
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
