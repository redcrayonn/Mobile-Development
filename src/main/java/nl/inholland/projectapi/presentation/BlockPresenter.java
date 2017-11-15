package nl.inholland.projectapi.presentation;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import nl.inholland.projectapi.model.BuildingBlock;
import nl.inholland.projectapi.presentation.model.BlockView;

public class BlockPresenter extends BasePresenter {

    private final ComponentPresenter presenter;

    @Inject
    public BlockPresenter(ComponentPresenter presenter) {
        this.presenter = presenter;
    }

    public BlockView present(BuildingBlock block) {
        BlockView view = new BlockView();
        view.id = block.getId();
        view.name = block.getName();
        view.description = block.getDescription();
        view.imageURL = block.getImageURL();
        view.components = presenter.present(block.getComponents());
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
