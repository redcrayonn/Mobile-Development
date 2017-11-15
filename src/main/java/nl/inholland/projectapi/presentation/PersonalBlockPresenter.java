package nl.inholland.projectapi.presentation;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import nl.inholland.projectapi.model.BuildingBlock;
import nl.inholland.projectapi.presentation.model.PersonalBlockView;

public class PersonalBlockPresenter extends BasePresenter {

    private final PersonalComponentPresenter componentPresenter;

    @Inject
    public PersonalBlockPresenter(PersonalComponentPresenter componentPresenter) {
        this.componentPresenter = componentPresenter;
    }

    public PersonalBlockView present(BuildingBlock block) {
        PersonalBlockView view = new PersonalBlockView();
        view.id = block.getId();
        view.name = block.getName();
        view.description = block.getDescription();
        view.imageURL = block.getImageURL();
        view.components = componentPresenter.present(block.getComponents());
        return view;
    }

    public List<PersonalBlockView> present(List<BuildingBlock> blocks) {
        List<PersonalBlockView> views = new ArrayList<>();

        for (BuildingBlock block : blocks) {
            views.add(present(block));
        }

        return views;
    }
}
