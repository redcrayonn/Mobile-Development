package nl.inholland.projectapi.presentation;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import nl.inholland.projectapi.model.Component;
import nl.inholland.projectapi.presentation.model.ComponentView;

public class ComponentPresenter extends BasePresenter {
    
    private final ActivityPresenter activityPresenter;
    
    @Inject
    public ComponentPresenter(ActivityPresenter activityPresenter) {
        this.activityPresenter = activityPresenter;
    }

    public ComponentView present(Component component) {
        ComponentView view = new ComponentView();
        view.id = component.getId();
        view.name = component.getName();
        view.activities = activityPresenter.present(component.getActivities());
        return view;
    }

    public List<ComponentView> present(List<Component> components) {
        List<ComponentView> views = new ArrayList<>();

        for (Component a : components) {
            views.add(present(a));
        }

        return views;
    }
    
}
