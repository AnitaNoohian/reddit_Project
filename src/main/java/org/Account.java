import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Account implements AccountManagement{
    private String email;
    private String password;
    private String ID;
    private String name;
    private List<Posts> postsList;
    private List<Subreddit> subreddits;
    private HashMap<UUID, Boolean> votes;
    private HashMap<UUID, String> comments;

    public void setID(String ID){
        this.ID = ID;
    }
    public void setPassword(String password){
        this.password = password;
    }
    @Override
    public String getName() {
        return name;
    }
    public String getEmail(){
        return email;
    }
    public void setName(String name){
        this.name = name;
    }
    public String setEmail(String email){
        if (checkEmail(email)) {
            this.email = email;
            return "Correct email!";
        }
        else {
            return "You entered a wrong email!Please try again.";
        }
    }
    public boolean checkEmail(String email){
        String regex = "(([^@#^&*~`()={}'\";:<>?]|(\".*\")){1,64})@(([^@_]+)|(\\[.+\\]))";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);

        if (matcher.find()){
            if (matcher.group().equals(email)){
                return true;
            }
            else {
                return false;
            }
        }
        else{
            return false;
        }
    }


}
