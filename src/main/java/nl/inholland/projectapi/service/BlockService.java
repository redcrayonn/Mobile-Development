/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.inholland.projectapi.service;

import java.util.List;
import javax.inject.Inject;
import nl.inholland.projectapi.model.Block;
import nl.inholland.projectapi.persistence.BlockDAO;

/**
 *
 * @author student
 */
public class BlockService extends BaseService {
        private BlockDAO dao;
   
    @Inject
    public BlockService(BlockDAO dao) {
        this.dao = dao;
    }
    
    public List<Block> getAllBlocks() {
        return dao.getAllBlocks();
    }
    
    public Block getBlockById(int id) {
        return dao.getBlockById(id);
    }
}
