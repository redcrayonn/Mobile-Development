package nl.inholland.projectapi.service;

import java.util.List;
import javax.inject.Inject;
import nl.inholland.projectapi.model.BuildingBlock;
import nl.inholland.projectapi.persistence.BlockDAO;

public class BlockService extends BaseService {
    private BlockDAO dao;
   
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
}
