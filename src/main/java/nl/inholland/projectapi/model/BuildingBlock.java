/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.inholland.projectapi.model;

import java.util.List;
import org.bson.types.ObjectId;

/**
 *
 * @author student
 */
public class BuildingBlock {
    private ObjectId id;
    private String name;
    private String description;
    private String imageURL;
    private List<Activity> activities;

    public BuildingBlock(ObjectId id, String name, String description, String imageURL, List<Activity> activities) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.imageURL = imageURL;
        this.activities = activities;
    }
    public BuildingBlock()
    {
        
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
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

    public List<Activity> getActivities() {
        return activities;
    }

    public void setActivities(List<Activity> activities) {
        this.activities = activities;
    }
}


