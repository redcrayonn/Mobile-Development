package nl.inholland.projectapi.presentation;

import java.util.ArrayList;
import java.util.List;
import nl.inholland.projectapi.model.Comment;
import nl.inholland.projectapi.presentation.model.CommentView;

public class CommentPresenter extends BasePresenter
{
    public CommentView present(Comment comment) {
        CommentView view = new CommentView();
        view.id = comment.getId().toHexString();
        view.senderId = comment.getSenderId().toHexString();
        view.message = comment.getMessage();
        view.dateTime = comment.getDateTime();
        return view;
    }
    
    public List<CommentView> present(List<Comment> comments) {
        List<CommentView> views = new ArrayList<>();
        
        for(Comment comment : comments) {
            CommentView view = new CommentView();
            view.id = comment.getId().toHexString();
            view.senderId = comment.getSenderId().toHexString();
            view.message = comment.getMessage();
            view.dateTime = comment.getDateTime();
            views.add(view);            
        }      
        return views;
    }      
}
