import java.util.UUID;

public class Comment {
    private Posts post;
    private Account user;
    private String detail;
    private int vote;
    private UUID ID;
    public Comment(Posts post,Account user,String detail){
        this.detail = detail;
        this.post = post;
        this.user = user;
        vote = 0;
        ID = UUID.randomUUID();
    }
    public UUID getID(){
        return ID;
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
    public String getDetail(){
        return detail;
    }
    public Account getUser(){
        return user;
    }
    public Posts getPost(){
        return post;
    }
    public void show(){
        System.out.println(user.getID());
        System.out.println(detail);
        System.out.println("Votes: " + vote);
    }
}
