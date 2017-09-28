package nl.inholland.projectapi.persistence;

import javax.inject.Inject;
import nl.inholland.projectapi.model.Client;
import org.mongodb.morphia.Datastore;

public class ClientDAO extends BaseDAO<Client>{
    
    @Inject
    public ClientDAO(Datastore ds)
    {
        super(Client.class, ds);
    }
    
}
