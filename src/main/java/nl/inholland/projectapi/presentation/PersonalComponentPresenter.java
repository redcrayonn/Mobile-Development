package nl.inholland.projectapi.presentation;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import nl.inholland.projectapi.model.Component;
import nl.inholland.projectapi.presentation.model.PersonalComponentView;

public class PersonalComponentPresenter extends BasePresenter {
    
    private final PersonalActivityPresenter activityPresenter;
    
    @Inject
    public PersonalComponentPresenter(PersonalActivityPresenter activityPresenter) {
        this.activityPresenter = activityPresenter;
    }

    public PersonalComponentView present(Component component) {
        PersonalComponentView view = new PersonalComponentView();
        view.id = component.getId();
        view.name = component.getName();
        view.status = component.getStatus();
        view.activities = activityPresenter.present(component.getActivities());
        return view;
    }

    public List<PersonalComponentView> present(List<Component> components) {
        List<PersonalComponentView> views = new ArrayList<>();
        for (Component component : components) {
            views.add(present(component));
        }
        return views;
    }    
}
