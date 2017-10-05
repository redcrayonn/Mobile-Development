package nl.inholland.projectapi.model;

import org.mongodb.morphia.annotations.Entity;

@Entity(value = "users", noClassnameStored = false)
public class Family extends User {

    public Family(Credentials credentials) {
        super(credentials);
    }
    public Family() {
        
    }

}
