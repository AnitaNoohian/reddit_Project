import java.io.Serializable;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Account implements Serializable {
    private int karmaP;
    private int karmaC;
    private String email;
    private String password;
    private String ID;
    private String name;
    private List<Posts> postsList;
    private List<Subreddit> mySubreddits;
    private List<Subreddit> subreddits;
    public HashMap<UUID, Boolean> votes;
    private List<Comment> comments;

    public Account(String name, String password, String email){
        karmaC = 0;
        karmaP = 0;
        this.name = name;
        this.password = hashPassword(password);
        this.email = email;
        postsList = new ArrayList<>();
        subreddits = new ArrayList<>();
        mySubreddits = new ArrayList<>();
        votes = new HashMap<>();
        comments = new ArrayList<>();
    }
    public boolean setID(String ID){
        boolean check = true;
        for (int i = 0; i < Info.users.size(); i++) {
            if (Info.users.get(i).getID().equals(ID)){
                check = false;
                break;
            }
        }
        if (check){
            this.ID = ID;
            System.out.println("Your registration was successful!\n");
            return true;
        } else {
            System.out.println("This username already token, enter another one!\n");
            return false;
        }
    }
    public String getID(){
        return ID;
    }
    public void setPassword(String password){
        this.password = hashPassword(password);
    }
    private String hashPassword(String password){
        long mod = 1000000009, mabna = 203,
                pow = 1, pass = 0;
        for (int i = 0; i < password.length(); i++){
            int save = password.charAt(i) - '0';
            pass = (pass + (pow * save)) % mod;
            pow = (pow * mabna) % mod;
        }
        return String.valueOf(pass);
    }
    public boolean checkPassword(String pass){
        if (hashPassword(pass).equals(password)){
            return true;
        } else {
            return false;
        }
    }
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
        String regex = "(([^@#^&*~`()={}'\";:<>?]|(\".*\")){1,64})@(([^@_]+\\..+)|(\\[.+\\]))";

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
    public List<Subreddit> subsData(){
        return subreddits;
    }
    public List<Subreddit> getMySubreddits(){
        return mySubreddits;
    }
    public List<Posts> postsData(){
        return postsList;
    }
    public List<Comment> getComments(){
        return comments;
    }
    public void createPost(Account user, Subreddit subreddit){
        Scanner input = new Scanner(System.in);
        Scanner input1 = new Scanner(System.in);
        System.out.println("Enter a title for your post:");
        String title = input.nextLine();
        System.out.println("Description:");
        String text = input1.nextLine();
        Posts post = new Posts(title,text,subreddit,user);
        postsList.add(post);
        subreddit.addPost(post);
        Info.posts.add(post);
    }
    public void createPost(Account user){
        Scanner input = new Scanner(System.in);
        Scanner input1 = new Scanner(System.in);
        System.out.println("Enter a title for your post:");
        String title = input.nextLine();
        System.out.println("Description:");
        String text = input1.nextLine();
        Posts post = new Posts(title,text,user);
        postsList.add(post);
        Info.posts.add(post);
    }
    public void createSubreddit(Account user){
        Scanner input = new Scanner(System.in);
        System.out.println("Enter the name of your subreddit:");
        String name = input.nextLine();
        if (name.equals("BACK") || name.equals("back") || name.equals("Back")) {

        } else {
            Subreddit subreddit = new Subreddit(name, user);
            mySubreddits.add(subreddit);
            Info.subreddits.add(subreddit);
        }
    }
    public void joinSubreddit(Subreddit subreddit, Account user){
        subreddits.add(subreddit);
        subreddit.addMembers(user);
    }
    public void addComment(Posts post, Account user){
        Scanner input = new Scanner(System.in);
        System.out.println("\nWhat is your opinion about this post?");
        String detail = input.nextLine();
        Comment comment = new Comment(post,user,detail);
        comments.add(comment);
        post.addComment(comment);
    }
    public void downVoteComment(Comment comment){
        comment.getUser().minusKarma("C");
        comment.downVote();
        votes.put(comment.getID(),false);
    }
    public void downVotePost(Posts post){
        post.getUser().minusKarma("P");
        post.downVote();
        votes.put(post.getID(),false);
    }
    public void minusKarma(String separator){
        if (separator.equals("P")) {
            karmaP--;
        }
        else {
            karmaC--;
        }
    }
    public void upVoteComment(Comment comment){
        comment.getUser().plusKarma("C");
        comment.upVote();
        votes.put(comment.getID(),true);
    }
    public void upVotePost(Posts post){
        post.getUser().plusKarma("P");
        post.upVote();
        votes.put(post.getID(),true);
    }
    public void plusKarma(String separator){
        if (separator.equals("P")) {
            karmaP++;
        }
        else {
            karmaC++;
        }
    }
    public int getKarma(String separator){
        if (separator.equals("K")){
            return karmaC+karmaP;
        } else if (separator.equals("P")){
            return karmaP;
        } else {
            return karmaC;
        }
    }

}
