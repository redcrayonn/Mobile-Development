package nl.inholland.projectapi.presentation;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import nl.inholland.projectapi.model.Family;
import nl.inholland.projectapi.presentation.model.FamilyView;

public class FamilyPresenter extends BasePresenter {

    private final MessagePresenter messagePresenter;
    
    @Inject
    public FamilyPresenter(MessagePresenter messagePresenter) {
        this.messagePresenter = messagePresenter;
    }

    public FamilyView present(Family family) {
        FamilyView view = new FamilyView();
        view.id = family.getId();
        view.name = family.getName();
        view.role = family.getRole();
        view.messages = messagePresenter.present(family.getMessages());
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
