package nl.inholland.projectapi.persistence;

import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import nl.inholland.projectapi.model.Client;
import nl.inholland.projectapi.model.Role;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;

public class ClientDAO extends BaseDAO<Client> {

    @Inject
    public ClientDAO(Datastore ds) {
        super(Client.class, ds);
    }
    public List<Client> getAllClients() {
        Query<Client> query = createQuery().field("role").equal(Role.client);
        return query.asList();
    }
    public Client getById(String id) {
        Query<Client> query;
        try {
        query = createQuery().field("role").equal(Role.client).field("_id").equal(new ObjectId(id));
        }catch(Exception e){
            throw new BadRequestException();
        }
        return findOne(query);
    }
}
