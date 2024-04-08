import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class Subreddit {
    private int numOfMembers;
    private String name;
    private Account host;
    protected List<Account> admins;
    protected List<Posts> posts;

    public Subreddit(String name, Account host){
        numOfMembers = 0;
        this.name = name;
        this.host = host;
        posts = new ArrayList<>();
    }

    public String getName(){
        return name;
    }
    public Account getHost(){
        return host;
    }
    public int getNumOfMembers(){
        return numOfMembers;
    }
    public void addMembers(){
        numOfMembers++;
    }
    public List<Posts> getPosts(){
        return posts;
    }
    public void addPost(Posts post){
        posts.add(post);
    }
}
