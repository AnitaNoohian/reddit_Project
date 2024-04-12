import java.io.Serializable;
import java.util.*;

public class Subreddit implements Serializable {
    private int numOfMembers;
    private String name;
    private Account host;
    protected List<Account> admins;
    private List<Account> members;
    protected List<Posts> posts;

    public Subreddit(String name, Account host){
        numOfMembers = 1;
        this.name = name;
        this.host = host;
        posts = new ArrayList<>();
        members = new ArrayList<>();
        admins = new ArrayList<>();
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
    public void addMembers(Account member){
        numOfMembers++;
        members.add(member);
    }
    public List<Account> getMembers(){
        return members;
    }
    public List<Posts> getPosts(){
        return posts;
    }
    public void addPost(Posts post){
        posts.add(post);
    }
    public boolean checkAdmin(Account admin){
        if (admin.getID().equals(host.getID())){
            return true;
        } else {
            for (int i = 0; i < admins.size(); i++){
                if (admins.get(i).getID().equals(admin.getID())){
                    return true;
                }
            }
            return false;
        }
    }
    public void setAdmins(Account user){
        boolean admin = true;
        for (int i = 0; i < admins.size(); i++){
            if (user.getID().equals(admins.get(i).getID())){
                System.out.println("This user is already admin!");
                admin = false;
                System.out.println("Do you want to remove this account from admin?\n1.Yes\t2.No");
                Scanner input = new Scanner(System.in);
                int remove = input.nextInt();
                while (true) {
                    if (remove == 1) {
                        admins.remove(i);
                        break;
                    } else if (remove == 2) {
                        break;
                    } else {
                        System.out.println("You entered a wrong number!\n");
                    }
                }
            }
        }
        if (admin) {
            admins.add(user);
            System.out.println("This user is admin of this subreddit from now on!\n");
        }
    }
}
