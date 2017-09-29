package nl.inholland.projectapi.model;

import org.mongodb.morphia.annotations.Entity;

@Entity(value = "family", noClassnameStored = true)
public class Family extends User {

}
