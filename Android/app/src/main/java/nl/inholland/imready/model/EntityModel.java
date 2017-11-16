package nl.inholland.imready.model;

public class EntityModel {
    private String id;

    public EntityModel() { }

    public EntityModel(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
