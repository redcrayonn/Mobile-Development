package nl.inholland.projectapi.service;

import java.net.URI;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.core.UriInfo;
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

    public List<BuildingBlock> getAll(int count) {
        List<BuildingBlock> blocks = dao.getAll();
        return reduceList(blocks, count);
    }

    public BuildingBlock getById(String id) {
        BuildingBlock block = dao.get(id);
        requireResult(block, "Block not found");
        return block;
    }

    public URI create(BuildingBlock block, UriInfo uriInfo) {
        for (Activity a : block.getActivities()) {
            a.setId(new ObjectId());
        }
        try {
            dao.create(block);
        } catch (Exception e) {
            throw new BadRequestException();
        }
        return buildUri(uriInfo, block.getId());
    }

    public void update(String id, BuildingBlock newBlock) {
        getById(id);
        try {
            dao.update(newBlock);
        } catch (IllegalArgumentException e) {
            throw new BadRequestException();
        }
    }

    public void deleteById(ObjectId id) {
        getById(id.toString());
        dao.deleteById(id);
    }
}
