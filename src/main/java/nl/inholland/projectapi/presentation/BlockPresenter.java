package nl.inholland.projectapi.presentation;

import java.util.ArrayList;
import java.util.List;
import nl.inholland.projectapi.model.BuildingBlock;
import nl.inholland.projectapi.presentation.model.BlockView;

public class BlockPresenter extends BasePresenter {

    public BlockPresenter() {
    }

    public BlockView present(BuildingBlock block) {
        BlockView view = new BlockView();
        view.id = block.getId();
        view.name = block.getName();
        view.description = block.getDescription();
        view.activities = block.getActivities();
        return view;
    }

    public List<BlockView> present(List<BuildingBlock> blocks) {
        List<BlockView> views = new ArrayList<>();

        for (BuildingBlock block : blocks) {
            views.add(present(block));
        }

        return views;
    }
}
