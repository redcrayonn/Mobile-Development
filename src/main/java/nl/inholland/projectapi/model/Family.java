package nl.inholland.projectapi.model;

import java.util.List;
import org.mongodb.morphia.annotations.Entity;

@Entity(value = "users", noClassnameStored = false)
public class Family extends User {

    public Family(Credentials credentials) {
        super(credentials, Role.family);
    }

    public Family() {

    }

}
