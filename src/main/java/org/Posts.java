import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class Posts implements PostManagement{
    private String title;
    private String text;
    private Subreddit subreddit;
    private Account user;
    private UUID ID;
    private HashMap<String, String> comments;

    public Posts(){
        ID = UUID.randomUUID();
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

}
