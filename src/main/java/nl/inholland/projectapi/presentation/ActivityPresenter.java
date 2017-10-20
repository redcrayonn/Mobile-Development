package nl.inholland.projectapi.presentation;

import java.util.ArrayList;
import java.util.List;
import nl.inholland.projectapi.model.Activity;
import nl.inholland.projectapi.presentation.model.ActivityView;

public class ActivityPresenter extends BasePresenter {

    public ActivityPresenter() {
    }

    public ActivityView present(Activity activity) {
        ActivityView view = new ActivityView();
        view.id = activity.getId();
        view.name = activity.getName();
        view.description = activity.getDescription();
        return view;
    }

    public List<ActivityView> present(List<Activity> activities) {
        List<ActivityView> views = new ArrayList<>();

        for (Activity a : activities) {
            views.add(present(a));
        }

        return views;
    }
}
