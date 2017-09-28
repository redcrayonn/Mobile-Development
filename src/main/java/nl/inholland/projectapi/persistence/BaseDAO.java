package nl.inholland.projectapi.persistence;

import java.util.List;
import javax.inject.Singleton;
import nl.inholland.projectapi.model.EntityModel;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;

@Singleton
public abstract class BaseDAO< T extends EntityModel> extends BasicDAO<T, ObjectId> 
{
    public BaseDAO(Class<T> entityClass, Datastore ds)
    {
        super(entityClass, ds);
    }
    public T get(String id)
    {
        return get(new ObjectId(id));
    }
    public List<T> getAll()
    {
        return find().asList();
    }
    public void create(T obj)
    {
        save(obj);
    }
    public void update(T obj)
    {
        save(obj);
    }
}
