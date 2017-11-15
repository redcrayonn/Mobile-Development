package nl.inholland.projectapi.model;

import io.dropwizard.validation.OneOf;
import java.util.ArrayList;
import java.util.List;
import nl.inholland.projectapi.model.inputs.InputComponent;
import org.mongodb.morphia.annotations.Embedded;

public class Component extends EntityModel {
    
    private String name;
    @OneOf({"ongoing", "pending", "complete", "irrelevant"})
    private Status status;
    @Embedded
    private List<Activity> activities;

    public Component() {
    }
    
    public Component(InputComponent input) {
        this.name = input.getName();
        this.activities = new ArrayList<>();
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<Activity> getActivities() {
        return activities;
    }

    public void setActivities(List<Activity> activities) {
        this.activities = activities;
    }
    
    
}
