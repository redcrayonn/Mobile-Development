package nl.inholland.projectapi.model;

import io.dropwizard.validation.OneOf;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.validator.constraints.NotEmpty;
import org.mongodb.morphia.annotations.Embedded;

public class Activity extends EntityModel {

    @NotEmpty
    private String name;
    @NotEmpty
    private String description;
    @OneOf({"ongoing", "pending", "complete", "irrelevant"})
    private Status status;
    @Embedded
    private List<Like> likes = new ArrayList<Like>();
    @Embedded
    private List<Comment> comments = new ArrayList<Comment>();

    public Activity() {

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

    public List<Like> getLikes() {
        return likes;
    }

    public void setLikes(List<Like> likes) {
        this.likes = likes;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
