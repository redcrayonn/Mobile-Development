package nl.inholland.projectapi.persistence;

import javax.inject.Inject;
import nl.inholland.projectapi.model.BuildingBlock;
import org.mongodb.morphia.Datastore;

public class BlockDAO extends BaseDAO<BuildingBlock> {

    @Inject
    public BlockDAO(Datastore ds) {
        super(BuildingBlock.class, ds);
    }
    
    
}
