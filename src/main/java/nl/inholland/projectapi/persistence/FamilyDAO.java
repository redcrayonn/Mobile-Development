package nl.inholland.projectapi.persistence;

import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import nl.inholland.projectapi.model.Family;
import nl.inholland.projectapi.model.Role;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;

public class FamilyDAO extends BaseDAO<Family> {

    @Inject
    public FamilyDAO(Datastore ds) {
        super(Family.class, ds);
    }

    public List<Family> getAllFamilies() {
        Query<Family> query = createQuery().field("role").equal(Role.family);
        return query.asList();
    }

    public Family getById(String id) {
        Query<Family> query;
        try {
            query = createQuery().field("role").equal(Role.family).field("_id").equal(new ObjectId(id));
        } catch (Exception e) {
            throw new BadRequestException();
        }
        return findOne(query);
    }
}
