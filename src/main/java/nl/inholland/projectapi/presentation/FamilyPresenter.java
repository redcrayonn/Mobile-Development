package nl.inholland.projectapi.presentation;

import java.util.ArrayList;
import java.util.List;
import nl.inholland.projectapi.model.Family;
import nl.inholland.projectapi.presentation.model.FamilyView;

public class FamilyPresenter extends BasePresenter {

    public FamilyView present(Family family) {
        FamilyView view = new FamilyView();
        view.id = family.getId();
        view.name = family.getName();
        view.role = family.getRole();
        return view;
    }

    public List<FamilyView> present(List<Family> family) {
        List<FamilyView> views = new ArrayList<>();

        for (Family f : family) {
            views.add(present(f));
        }

        return views;
    }
}
