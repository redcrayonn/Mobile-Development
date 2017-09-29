package nl.inholland.projectapi.presentation;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import nl.inholland.projectapi.model.Family;
import nl.inholland.projectapi.presentation.model.FamilyView;

public class FamilyPresenter extends BasePresenter {

    @Inject
    public FamilyPresenter() {
    }

    public FamilyView present(Family family) {
        FamilyView view = new FamilyView();
        view.id = family.getId();
        view.name = family.getName();
        view.messages = family.getMessages();
        return view;
    }

    public List<FamilyView> present(List<Family> family) {
        List<FamilyView> views = new ArrayList<>();

        for (Family f : family) {
            FamilyView view = new FamilyView();
            view.id = f.getId();
            view.name = f.getName();
            view.messages = f.getMessages();
            views.add(view);
        }

        return views;
    }
}
