/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.inholland.projectapi.persistence;

import java.util.List;
import nl.inholland.projectapi.model.Block;

/**
 *
 * @author student
 */
public class BlockDAO extends BaseDAO{
    public Block getBlockById(int id) {
        return MockDB.getBlockById(id);
    }
    
    public List<Block> getAllBlocks() {
        return MockDB.getAllBlocks();
    }
}
