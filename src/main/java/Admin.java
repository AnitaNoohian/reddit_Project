public class Admin extends Subreddit{
    public Admin(String name, Account host) {
        super(name, host);
    }
    public void addAdmin(Account user){
        admins.add(user);
    }
}
