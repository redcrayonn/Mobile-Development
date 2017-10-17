package nl.inholland.projectapi.persistence;

import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import nl.inholland.projectapi.model.Caregiver;
import nl.inholland.projectapi.model.Client;
import org.mongodb.morphia.Datastore;
import nl.inholland.projectapi.model.Role;
import org.bson.types.ObjectId;
import org.mongodb.morphia.query.Query;

public class CaregiverDAO extends BaseDAO<Caregiver> {

    @Inject
    public CaregiverDAO(Datastore ds) {
        super(Caregiver.class, ds);
    }

    public List<Caregiver> getAllCaregivers() {
        Query<Caregiver> query = createQuery().field("role").equal(Role.caregiver);
        return query.asList();
    }

    public Caregiver getById(String id) {
        Query<Caregiver> query;
        try {
            query = createQuery().field("role").equal(Role.caregiver).field("_id").equal(new ObjectId(id));
        } catch (Exception e) {
            throw new BadRequestException();
        }
        return findOne(query);
    }
}
