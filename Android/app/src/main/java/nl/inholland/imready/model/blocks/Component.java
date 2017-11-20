package nl.inholland.imready.model.blocks;

import java.util.ArrayList;
import java.util.List;

import nl.inholland.imready.model.NamedEntityModel;

public class Component extends NamedEntityModel {
    private BlockPartStatus status = BlockPartStatus.IRRELEVANT;
    private List<Activity> activities = new ArrayList<>();

    public Component() {
    }

    public Component(String name) {
        super(name);
    }

    public Component(String id, String name) {
        super(id, name);
    }

    public List<Activity> getActivities() {
        return activities;
    }

    public void setActivities(List<Activity> activities) {
        this.activities = activities;
    }

    public BlockPartStatus getStatus() {
        return status;
    }

    public void setStatus(BlockPartStatus status) {
        this.status = status;
    }
}
