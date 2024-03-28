import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class Subreddit {
    private int numOfMembers;
    private String name;
    private String host;
    protected List<Account> admins;
    protected HashMap<UUID, Posts> posts;

    public Subreddit(String name, String host){
        numOfMembers = 0;
        this.name = name;
        this.host = host;
    }

    public String getName(){
        return name;
    }
    public String getHost(){
        return host;
    }
    public int getNumOfMembers(){
        return numOfMembers;
    }
    public void addMembers(){
        numOfMembers++;
    }
}
