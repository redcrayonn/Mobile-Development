/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.inholland.projectapi.presentation;

import java.util.ArrayList;
import java.util.List;
import nl.inholland.projectapi.model.Block;
import nl.inholland.projectapi.presentation.model.BlockView;

/**
 *
 * @author student
 */
public class BlockPresenter extends BasePresenter {
    public String present(Block block) {
        BlockView view = new BlockView();
        
        view.name = block.getName();
        view.description = block.getDescription();
        return super.toJson(view);
    }
    
    public List<BlockView> present(List<Block> blocks) {
        List<BlockView> views = new ArrayList<>();
        
        for(Block block : blocks) {
            BlockView view = new BlockView();
            
            view.name = block.getName();
            view.description = block.getDescription();
        
            views.add(view);            
        }
        
        return views;
    }
}
