package nl.inholland.projectapi.service;

import java.util.List;
import javax.inject.Inject;
import nl.inholland.projectapi.model.Activity;
import nl.inholland.projectapi.model.BuildingBlock;
import nl.inholland.projectapi.persistence.BlockDAO;
import org.bson.types.ObjectId;

public class BlockService extends BaseService {

    private final BlockDAO dao;

    @Inject
    public BlockService(BlockDAO dao) {
        this.dao = dao;
    }

    public List<BuildingBlock> getAllBlocks() {
        return dao.getAll();
    }

    public BuildingBlock getBlockById(String id) {
        return dao.get(id);
    }

    public void create(BuildingBlock block) {
        for (Activity a : block.getActivities()) {
            a.setId(new ObjectId());
        }
        dao.create(block);
    }

    public void update(BuildingBlock block) {
        dao.update(block);
    }

    public void deleteById(ObjectId id) {
        dao.deleteById(id);
    }
}
