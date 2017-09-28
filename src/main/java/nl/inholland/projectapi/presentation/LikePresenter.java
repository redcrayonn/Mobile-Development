package nl.inholland.projectapi.presentation;

import java.util.ArrayList;
import java.util.List;
import nl.inholland.projectapi.model.Like;
import nl.inholland.projectapi.presentation.model.LikeView;

public class LikePresenter 
{
    public LikeView present(Like like) {
        LikeView view = new LikeView();
        view.id = like.getId().toHexString();
        view.senderId = like.getSenderId().toHexString();
        view.dateTime = like.getDateTime();
        return view;
    }
    
    public List<LikeView> present(List<Like> likes) {
        List<LikeView> views = new ArrayList<>();
        
        for(Like like : likes) {
            LikeView view = new LikeView();
            view.id = like.getId().toHexString();
            view.senderId = like.getSenderId().toHexString();
            view.dateTime = like.getDateTime();
            views.add(view);            
        }      
        return views;
    }     
}
