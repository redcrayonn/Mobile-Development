package nl.inholland.projectapi.presentation;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import nl.inholland.projectapi.model.Activity;
import nl.inholland.projectapi.presentation.model.PersonalActivityView;

public class PersonalActivityPresenter extends BasePresenter {

    private final LikePresenter likePresenter;
    private final CommentPresenter commentPresenter;

    @Inject
    public PersonalActivityPresenter(
            LikePresenter likePresenter,
            CommentPresenter commentPresenter) {
        this.likePresenter = likePresenter;
        this.commentPresenter = commentPresenter;
    }

    public PersonalActivityView present(Activity activity) {
        PersonalActivityView view = new PersonalActivityView();
        view.id = activity.getId();
        view.description = activity.getDescription();
        view.name = activity.getName();
        view.status = activity.getStatus();
        view.likes = likePresenter.present(activity.getLikes());
        view.comments = commentPresenter.present(activity.getComments());

        return view;
    }

    public List<PersonalActivityView> present(List<Activity> activities) {
        List<PersonalActivityView> views = new ArrayList<>();
        for (Activity activity : activities) {
            views.add(present(activity));
        }
        return views;
    }
}
