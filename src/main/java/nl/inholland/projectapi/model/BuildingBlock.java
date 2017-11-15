package nl.inholland.projectapi.model;

import java.util.ArrayList;
import java.util.List;
import nl.inholland.projectapi.model.inputs.InputBlock;
import org.hibernate.validator.constraints.NotEmpty;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;

@Entity(value = "buildingBlocks", noClassnameStored = true)
public class BuildingBlock extends EntityModel {

    @NotEmpty
    private String name;
    @NotEmpty
    private String description;
    @NotEmpty
    private String imageURL;
    @Embedded
    private List<Component> components;

    public BuildingBlock() {
    }
    
    public BuildingBlock(InputBlock input) {
        this.name = input.getName();
        this.description = input.getDescription();
        this.imageURL = input.getImageURL();
        this.components = new ArrayList<>();
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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
