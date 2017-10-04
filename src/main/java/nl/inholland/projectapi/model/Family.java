package nl.inholland.projectapi.model;

import org.mongodb.morphia.annotations.Entity;

@Entity(value = "users", noClassnameStored = false)
public class Family extends User {

}
