package nl.inholland.imready.model.blocks;

import java.util.List;

import nl.inholland.imready.model.NamedEntityModel;

public class Block extends NamedEntityModel {
    private String description;
    private String imageURL;
    private List<Component> components;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public List<Component> getComponents() {
        return components;
    }

    public void setComponents(List<Component> components) {
        this.components = components;
    }
}
