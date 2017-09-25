/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.inholland.projectapi.model;

/**
 *
 * @author Stefan
 */
public class Activity {
    private String name;
    private String description;
    private Status status;

    public Activity(String name, String description, Status status) {
        this.name = name;
        this.description = description;
        this.status = status;
    }
    public Activity()
    {
        
    }
    
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
