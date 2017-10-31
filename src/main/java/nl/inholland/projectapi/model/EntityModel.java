package nl.inholland.projectapi.model;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Id;

public class EntityModel {

    @Id
    protected ObjectId id;

    public String getId() {
        return id.toString();
    }

    public void setId(ObjectId id) {
        this.id = id;
    }
    
    public void createNewId() {
        this.id = new ObjectId();
    }
}
