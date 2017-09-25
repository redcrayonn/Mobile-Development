package nl.inholland.projectapi.persistence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Singleton;
import nl.inholland.projectapi.model.Block;

@Singleton
public class MockDB
{
    
    public final static Map<Integer, Block> blocks = new HashMap<>();
    
    public static void init()
    {
        addBlock(1, "surpal", "surpaaaal", "http://google.nl/logo");
        addBlock(2, "lijstje", "blokkie","http://bla.nl");
    }

   
    private static void addBlock(int id, String name, String description, String imageURL) {
        Block block = new Block();
        block.setId(id);
        block.setName(name);
        block.setDescription(description);
        block.setImageURL(imageURL);
        
        blocks.put(id, block);
    }
    
    public static List<Block> getAllBlocks() {
        return new ArrayList<>(blocks.values());
    }
    
    public static Block getBlockById(int id) {
        for (Block block : blocks.values()) {
            
            if(block.getId() == id) {
                return block;
            }
        }
        
        return null;
    }
}
