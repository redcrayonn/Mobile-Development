package nl.inholland.projectapi.presentation.model;

import java.util.List;

public class ActivityView 
{
    public String id;
    public String name;
    public String description;
    public List<CommentView> comments;
    public List<LikeView> likes;
}
