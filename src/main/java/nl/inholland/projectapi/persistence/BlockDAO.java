/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.inholland.projectapi.persistence;

import com.mongodb.client.MongoCursor;
import java.util.ArrayList;
import java.util.List;
import nl.inholland.projectapi.model.BuildingBlock;

/**
 *
 * @author student
 */
public class BlockDAO extends BaseDAO{
    public BuildingBlock getBlockById(int id) {
        return null;
    }
    
    public List<BuildingBlock> getAllBlocks() {
        List<BuildingBlock> blocks = new ArrayList<>();
        MongoDB mongo = MongoDB.getInstance();
        MongoCursor<BuildingBlock> cursor = mongo.getBuildingBlocks().find().iterator();
        while(cursor.hasNext())
        {
            blocks.add(cursor.next());
        }
        cursor.close();
        return blocks;
    }
}
