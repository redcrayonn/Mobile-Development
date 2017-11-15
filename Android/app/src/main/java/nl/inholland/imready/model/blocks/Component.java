package nl.inholland.imready.model.blocks;

import java.util.List;

import nl.inholland.imready.model.NamedEntityModel;

public class Component extends NamedEntityModel {
    private List<Activity> activities;

    public List<Activity> getActivities() {
        return activities;
    }

    public void setActivities(List<Activity> activities) {
        this.activities = activities;
    }
}
