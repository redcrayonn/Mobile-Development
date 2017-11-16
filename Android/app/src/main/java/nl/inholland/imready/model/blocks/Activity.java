package nl.inholland.imready.model.blocks;

import nl.inholland.imready.model.NamedEntityModel;

public class Activity extends NamedEntityModel {
    private String description;
    private int points;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
