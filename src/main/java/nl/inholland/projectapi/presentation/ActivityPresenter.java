package nl.inholland.projectapi.presentation;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import nl.inholland.projectapi.model.Activity;
import nl.inholland.projectapi.presentation.model.ActivityView;

public class ActivityPresenter extends BasePresenter
{
    private final CommentPresenter commentPresenter;
    private final LikePresenter likePresenter;
    
    @Inject
    public ActivityPresenter(CommentPresenter commentPresenter, LikePresenter likePresenter)
    {
        this.commentPresenter = commentPresenter;
        this.likePresenter = likePresenter;
    }
    public ActivityView present(Activity activity) {
        ActivityView view = new ActivityView();
        view.id = activity.getId().toHexString();
        view.name = activity.getName();
        view.description = activity.getDescription();
        if(activity.getComments() == null)
        {
            activity.setComments(new ArrayList<>());
        }
        view.comments = commentPresenter.present(activity.getComments());
        if(activity.getLikes() == null)
        {
            activity.setLikes(new ArrayList<>());
        }
        view.likes = likePresenter.present(activity.getLikes());
        return view;
    }
    
    public List<ActivityView> present(List<Activity> activities) {
        List<ActivityView> views = new ArrayList<>();
        
        for(Activity activity : activities) {
            ActivityView view = new ActivityView();
            view.id = activity.getId().toHexString();
            view.name = activity.getName();
            view.description = activity.getDescription();
            if(activity.getComments() == null)
            {
                activity.setComments(new ArrayList<>());
            }
            view.comments = commentPresenter.present(activity.getComments());
            if(activity.getLikes() == null)
            {
                activity.setLikes(new ArrayList<>());
            }
            view.likes = likePresenter.present(activity.getLikes());
            views.add(view);            
        }
        
        return views;
    }    
}
