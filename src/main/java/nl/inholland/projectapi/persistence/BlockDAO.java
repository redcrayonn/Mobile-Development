package nl.inholland.projectapi.persistence;

import java.util.List;
import nl.inholland.projectapi.model.Block;


public class BlockDAO extends BaseDAO {

    public Block getBlockById(int id) {
        return MockDB.getBlockById(id);
    }

    public List<Block> getAllBlocks() {
        return MockDB.getAllBlocks();
    }

}
