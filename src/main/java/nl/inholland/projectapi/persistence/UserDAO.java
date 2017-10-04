package nl.inholland.projectapi.persistence;

import javax.inject.Inject;
import nl.inholland.projectapi.model.Credentials;
import nl.inholland.projectapi.model.User;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;

/**
 *
 * @author student
 */


public class UserDAO extends BaseDAO<User>
{ 
    @Inject
    public UserDAO(Datastore ds) {
        super(User.class, ds);
    } 
    public User getByAPIKey(String token)
    {
        Query<User> query = createQuery()
                .field("apiKey").equal(token);
        return findOne(query);        
    }
    public User getByUsername(String username) {
        Query<User> query = createQuery()
                .field("name").equal(username);
        
        return findOne(query);
    }
    public User getByCredentials(Credentials credentials) {
        Query<User> query = createQuery()
                .field("name").equal(credentials.getUsername())
                .field("password").equal(credentials.getPassword());
        
        return findOne(query);
    }
}
