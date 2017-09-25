/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.inholland.projectapi.presentation;

import java.util.ArrayList;
import java.util.List;
import nl.inholland.projectapi.model.BuildingBlock;
import nl.inholland.projectapi.presentation.model.BlockView;
import org.bson.types.ObjectId;

/**
 *
 * @author student
 */
public class BlockPresenter extends BasePresenter {
    public String present(BuildingBlock block) {
        BlockView view = new BlockView();
        view.id = block.getId().toHexString();
        view.name = block.getName();
        view.description = block.getDescription();
        view.activities = block.getActivities();
        return super.toJson(view);
    }
    
    public List<BlockView> present(List<BuildingBlock> blocks) {
        List<BlockView> views = new ArrayList<>();
        
        for(BuildingBlock block : blocks) {
            BlockView view = new BlockView();
            view.id = block.getId().toHexString();
            view.name = block.getName();
            view.description = block.getDescription();
            view.activities = block.getActivities();
        
            views.add(view);            
        }
        
        return views;
    }
}
