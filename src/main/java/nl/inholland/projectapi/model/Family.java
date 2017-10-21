package nl.inholland.projectapi.model;

import nl.inholland.projectapi.model.inputs.Credentials;
import org.mongodb.morphia.annotations.Entity;

@Entity(value = "users", noClassnameStored = false)
public class Family extends User {

    public Family(Credentials credentials) {
        super(credentials, Role.family);
    }

    public Family() {

    }

}
