import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class Posts implements PostManagement{
    private int vote;
    private String title;
    private String text;
    private Subreddit subreddit;
    private Account user;
    private UUID ID;
    public List<Comment> comments;

    public Posts(String title, String text, Subreddit subreddit, Account user){
        this.title = title;
        this.text = text;
        ID = UUID.randomUUID();
        this.subreddit = subreddit;
        this.user = user;
        comments = new ArrayList<>();
    }
    public Posts(String title, String text, Account user){
        this.title = title;
        this.text = text;
        this.user = user;
        ID = UUID.randomUUID();
        subreddit = null;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public void setText(String text){
        this.text = text;
    }
    public String getTitle(){
        return title;
    }
    public String getText(){
        return text;
    }
    public String getUserName(){
        return user.getID();
    }
    public Account getUser(){
        return user;
    }
    public Subreddit getSubreddit(){
        return subreddit;
    }
    public UUID getID(){
        return ID;
    }
    public void addComment(Comment comment){
        comments.add(comment);
    }

    public void upVote(){
        vote++;
    }
    public void downVote(){
        vote--;
    }
    public int getVote(){
        return vote;
    }
}
