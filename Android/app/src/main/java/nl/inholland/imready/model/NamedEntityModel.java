package nl.inholland.imready.model;

public class NamedEntityModel extends EntityModel {
    private String name;

    public NamedEntityModel() {
    }

    public NamedEntityModel(String name) {
        this.name = name;
    }

    public NamedEntityModel(String id, String name) {
        super(id);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
