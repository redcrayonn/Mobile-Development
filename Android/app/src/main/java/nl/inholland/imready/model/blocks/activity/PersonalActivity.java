package nl.inholland.imready.model.blocks.activity;

import java.util.List;

import nl.inholland.imready.model.blocks.Activity;
import nl.inholland.imready.model.blocks.BlockPartStatus;

public class PersonalActivity extends Activity {
    private List<Likes> likes;
    private List<Comments> comments;

    public List<Likes> getLikes() {
        return likes;
    }

    public void setLikes(List<Likes> likes) {
        this.likes = likes;
    }

    public List<Comments> getComments() {
        return comments;
    }

    public void setComments(List<Comments> comments) {
        this.comments = comments;
    }
}
