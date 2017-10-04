package nl.inholland.projectapi.persistence;

import java.util.List;
import javax.inject.Inject;
import nl.inholland.projectapi.model.Client;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;

public class ClientDAO extends BaseDAO<Client> {

    @Inject
    public ClientDAO(Datastore ds) {
        super(Client.class, ds);
    }
    public List<Client> getAllClients()
    {
        Query<Client> query = createQuery().field("role").equal("client");
        return query.asList();
    }
}
