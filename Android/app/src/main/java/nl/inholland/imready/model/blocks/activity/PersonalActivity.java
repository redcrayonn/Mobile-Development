package nl.inholland.imready.model.blocks.activity;

import java.util.List;

import nl.inholland.imready.model.blocks.Activity;

public class PersonalActivity extends Activity {
    private String content;
    private ActivityStatus status;
    private List<Likes> likes;
    private List<Comments> comments;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ActivityStatus getStatus() {
        return status;
    }

    public void setStatus(ActivityStatus status) {
        this.status = status;
    }

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
