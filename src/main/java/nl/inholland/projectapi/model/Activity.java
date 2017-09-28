package nl.inholland.projectapi.model;

import io.dropwizard.validation.OneOf;
import org.hibernate.validator.constraints.NotEmpty;

public class Activity {
    @NotEmpty
    private String name;
    @NotEmpty
    private String description;
    @OneOf({"ongoing", "pending", "complete", "irrelevant"})
    private Status status;
    
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
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
}
