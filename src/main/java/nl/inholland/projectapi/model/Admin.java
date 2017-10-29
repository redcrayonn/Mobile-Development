package nl.inholland.projectapi.model;

import nl.inholland.projectapi.model.inputs.Credentials;
import org.mongodb.morphia.annotations.Entity;

@Entity(value = "users", noClassnameStored = false)
public class Admin extends User {

    public Admin(Credentials credentials) {
        super(credentials, Role.admin);
    }

    public Admin() {

    }
}
