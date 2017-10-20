package nl.inholland.projectapi.presentation;

import java.util.ArrayList;
import java.util.List;
import nl.inholland.projectapi.model.Like;
import nl.inholland.projectapi.presentation.model.LikeView;

public class LikePresenter extends BasePresenter {

    public LikeView present(Like like) {
        LikeView view = new LikeView();
        view.id = like.getId();
        view.datetime = like.getDateTime();
        view.senderId = like.getSenderId();
        return view;
    }

    public List<LikeView> present(List<Like> likes) {
        List<LikeView> views = new ArrayList<>();

        for (Like like : likes) {
            views.add(present(like));
        }
        return views;
    }
}
