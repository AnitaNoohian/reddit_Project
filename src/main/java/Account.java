import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Account implements AccountManagement{
    private int karmaP;
    private int karmaC;
    private String email;
    private String password;
    private String ID;
    private String name;
    private List<Posts> postsList;
    private List<Subreddit> subreddits;
    private HashMap<UUID, Boolean> votes;
    private HashMap<UUID, String> comments;

    public Account(String name){
        karmaC = 0;
        karmaP = 0;
        this.name = name;
        postsList = new ArrayList<>();
        subreddits = new ArrayList<>();
        votes = new HashMap<>();
        comments = new HashMap<>();
    }
    public boolean setID(String ID){
        boolean check = true;
        for (String key : Info.users.keySet()) {
            if (key.equals(ID)){
                check = false;
                break;
            }
        }
        if (check){
            this.ID = ID;
            System.out.println("Correct user!");
            return true;
        } else {
            System.out.println("This user already token, enter another one!");
            return false;
        }
    }
    public String getID(){
        return ID;
    }
    public void setPassword(String password){
        this.password = password;
    }
    public String getPassword(){
        return password;
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
    public List<String> getSubreddit(){
        List<String> subs = new ArrayList<>();
        for (int i = 0; i < subreddits.size(); i++){
            subs.add(subreddits.get(i).getName());
        }
        return subs;
    }
    public List<String> getPosts(){
        List<String> posts = new ArrayList<>();
        for (int i = 0; i < postsList.size(); i++){
            posts.add(postsList.get(i).getTitle());
        }
        return posts;
    }
    public List<Posts> postsData(){
        return postsList;
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
    }
    public void createSubreddit(Account user){
        Scanner input = new Scanner(System.in);
        System.out.println("Enter the name of your subreddit:");
        String name = input.nextLine();
        Subreddit subreddit = new Subreddit(name,user.ID);
        subreddits.add(subreddit);
        Info.subreddits.add(subreddit);
    }
    public void joinSubreddit(Subreddit subreddit){
        subreddits.add(subreddit);
        subreddit.addMembers();
    }
    public void addComment(Posts post, Account user){
        Scanner input = new Scanner(System.in);
        System.out.println("What is your opinion about this post?");
        String comment = input.nextLine();
        comments.put(post.getID(),comment);
        post.addComment(comment,user);
    }
    public void downVoteComment(Posts post){
        post.getUser().minusKarma("C");
    }
    public void downVotePost(Posts post){
        post.getUser().minusKarma("P");
    }
    public void minusKarma(String separator){
        if (separator.equals("P")) {
            karmaP--;
        }
        else {
            karmaC--;
        }
    }
    public void upVoteComment(Posts post){
        post.getUser().plusKarma("C");
    }
    public void upVotePost(Posts post){
        post.getUser().plusKarma("P");
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
