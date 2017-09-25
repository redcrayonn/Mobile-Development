/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.inholland.projectapi.persistence;

import com.mongodb.client.MongoCursor;
import static com.mongodb.client.model.Filters.eq;
import java.util.ArrayList;
import java.util.List;
import nl.inholland.projectapi.model.BuildingBlock;
import org.bson.types.ObjectId;

/**
 *
 * @author student
 */
public class BlockDAO extends BaseDAO{
    
    private MongoDB mongo = MongoDB.getInstance();
    
    public BuildingBlock getBlockById(String id) 
    {
        try
        {
            return mongo.getBuildingBlocks().find(eq("_id", new ObjectId(id))).first();           
        }
        catch(IllegalArgumentException ex)
        {
            return null;
        }
    }
    
    public List<BuildingBlock> getAllBlocks() {
        List<BuildingBlock> blocks = new ArrayList<>();
        MongoCursor<BuildingBlock> cursor = mongo.getBuildingBlocks().find().iterator();
        while(cursor.hasNext())
        {
            blocks.add(cursor.next());
        }
        cursor.close();
        return blocks;
    }
}
