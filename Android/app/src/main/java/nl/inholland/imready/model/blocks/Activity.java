package nl.inholland.imready.model.blocks;

import nl.inholland.imready.model.NamedEntityModel;

public class Activity extends NamedEntityModel {
    private String description;
    private int points;
    private BlockPartStatus status = BlockPartStatus.IRRELEVANT;
    private String content;
    private String assignment;

    public Activity() {
    }

    public Activity(String name) {
        super(name);
    }

    public Activity(String id, String name) {
        super(id, name);
    }

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

    public BlockPartStatus getStatus() {
        return status;
    }

    public void setStatus(BlockPartStatus status) {
        this.status = status;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAssignment() {
        return assignment;
    }

    public void setAssignment(String assignment) {
        this.assignment = assignment;
    }
}
