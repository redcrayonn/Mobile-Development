package nl.inholland.projectapi.presentation.model;

import java.util.List;
import nl.inholland.projectapi.model.Status;

public class ActivityView extends BaseView {

    public String id;
    public String name;
    public String description;
    public Status status;
    public List<CommentView> comments;
    public List<LikeView> likes;
}
