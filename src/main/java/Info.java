import java.io.Serializable;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Info implements Serializable {
    public static List<Subreddit> subreddits = new ArrayList<>();
    public static List<Account> users = new ArrayList<>();
    public static List<Posts> posts = new ArrayList<>();

    public static boolean checkID(String ID) {
        if (ID.length() < 4){
            return false;
        } else {
            String regex = "^\\d*([a-z]|[A-Z]){1}([a-z]|\\d|[A-Z])*$";

            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(ID);

            if (matcher.find()) {
                return true;
            } else {
                return false;
            }
        }
    }
    public static boolean checkPass(String pass){
        String regex = "^([a-z]|[A-Z]|\\d){8,}$";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(pass);

        if (matcher.find()) {
            return true;
        } else {
            return false;
        }
    }
    public static boolean checkEmail(String email){
        String regex = "^(([^@#^&*~`()={}'\";:<>?]|(\".*\")){1,64})@(([^@_]+\\..+)|(\\[.+\\]))$";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);

        if (matcher.find()) {
            return true;
        } else {
            return false;
        }
    }
    public static boolean findEmail(String email){
        boolean check = true;
        for (int i = 0; i < users.size(); i++){
            if (email.equals(users.get(i).getEmail())){
                check = false;
                break;
            }
        }
        if (check){
            return false;
        } else {
            return true;
        }
    }
    public static boolean findUser(String username){
        boolean check = true;
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getID().equals(username)){
                check = false;
                break;
            }
        }
        if (check){
            return false;
        } else {
            return true;
        }
    }
    public static void profile(Account user){
        pro:
        while (true) {
            System.out.println("\n\nName: " + user.getName());
            System.out.println("Username: " + user.getID());
            System.out.println("Email: " + user.getEmail());
            System.out.println("Your karma: " + user.getKarma("K") + "\n--> " + "Post's Karma: " + user.getKarma("P") + ",\t"
                    + "Comment's Karma: " + user.getKarma("C"));
            System.out.println("\nSubreddit you join - Subreddits you create - Posts - Comments - Back\n");
            System.out.println("Enter the name of the part you want:");
            Scanner input = new Scanner(System.in);
            String pro = input.nextLine();
            switch (pro) {
                case "Subreddit you join":
                    if (user.subsData().isEmpty()){
                        System.out.println("\nYou are not a member of Subreddit yet!");
                    } else {
                        for (int i = 0; i < user.subsData().size(); i++) {
                            System.out.println(i + 1 + ")" + user.subsData().get(i).getName() + "\n" +
                                    user.subsData().get(i).getNumOfMembers() + " Members");
                        }
                        System.out.println("\n[if you want to open a subreddit enter the number or enter 0 to go back to the profile]");
                        Scanner input1 = new Scanner(System.in);
                        int sub = input1.nextInt();
                        if (sub != 0) {
                            while (true) {
                                Subreddit subreddit = user.subsData().get(sub - 1);
                                System.out.println("\ns/" + subreddit.getName());
                                if (!subreddit.getPosts().isEmpty()) {
                                    System.out.println("\nPosts: ");
                                    for (int i = 0; i < subreddit.getPosts().size(); i++) {
                                        System.out.println(i + 1 + ")" + "Posted by u/" + subreddit.getPosts().get(i).getUserName());
                                        System.out.println("Title: " + subreddit.getPosts().get(i).getTitle());
                                        System.out.println("***\n" + subreddit.getPosts().get(i).getText() + "\n***");
                                        System.out.println("\nVotes: " + subreddit.getPosts().get(i).getVote());
                                    }
                                }
                                System.out.println("\nEnter the number to see the post - Post - Add new admins - Back");
                                Scanner input2 = new Scanner(System.in);
                                String num = input2.nextLine();
                                if (num.equals("Post")) {
                                    if (subreddit.checkAdmin(user)){
                                        user.createPost(user,subreddit);
                                    } else {
                                        System.out.println("You are not allowed to post in this subreddit.\n");
                                    }
                                } else if (num.equals("Add new admins")) {
                                    if (subreddit.checkAdmin(user)) {
                                        if (subreddit.getMembers().isEmpty()) {
                                            System.out.println("There is no member in this subreddit!\n");
                                        } else {
                                            System.out.println("Choose the number of a member(press 0 to go back):");
                                            for (int i = 0; i < subreddit.getMembers().size(); i++) {
                                                System.out.println(i + 1 + ") u/" + subreddit.getMembers().get(i).getID());
                                            }
                                            Scanner input4 = new Scanner(System.in);
                                            int mem = input4.nextInt();
                                            if (mem != 0) {
                                                Account member = subreddit.getMembers().get(mem - 1);
                                                subreddit.setAdmins(member);
                                            }
                                        }
                                    } else {
                                        System.out.println("You are not allowed to post in this subreddit.\n");
                                    }
                                } else if (num.equals("Back")){
                                    break;
                                } else if (num.isEmpty()) {

                                } else {
                                    char p = num.charAt(0);
                                    Posts post = subreddit.getPosts().get(p-49);
                                    postShow(post, user);
                                }
                            }
                        }
                    }
                    break;
                case "Subreddits you create":
                    if (user.getMySubreddits().isEmpty()){
                        System.out.println("\nYou have not created a subreddit yet!");
                    } else {
                        for (int i = 0; i < user.getMySubreddits().size(); i++) {
                            System.out.println(i + 1 + ")" + user.getMySubreddits().get(i).getName() + "\n" +
                                    user.getMySubreddits().get(i).getNumOfMembers() + " Members");
                        }
                        System.out.println("\n[if you want to open a subreddit enter the number or enter 0 to go back to the profile]");
                        Scanner input2 = new Scanner(System.in);
                        int sub1 = input2.nextInt();
                        if (sub1 != 0) {
                            while (true) {
                                Subreddit subreddit = user.getMySubreddits().get(sub1 - 1);
                                System.out.println("s/" + subreddit.getName() + "\n");
                                if (!subreddit.getPosts().isEmpty()) {
                                    System.out.println("\nPosts: ");
                                    for (int i = 0; i < subreddit.getPosts().size(); i++) {
                                        System.out.println(i + 1 + ")" + "Posted by u/" + subreddit.getPosts().get(i).getUserName());
                                        System.out.println("Title: " + subreddit.getPosts().get(i).getTitle());
                                        System.out.println("***\n" + subreddit.getPosts().get(i).getText() + "\n***");
                                        System.out.println("Votes: " + subreddit.getPosts().get(i).getVote());
                                    }
                                }
                                System.out.println("\nEnter the number to see the post - Post - Add new admins - Back");
                                Scanner input3 = new Scanner(System.in);
                                String num = input3.nextLine();
                                if (num.equals("Post")) {
                                    user.createPost(user,subreddit);
                                } else if (num.equals("Add new admins")) {
                                    if (subreddit.getMembers().isEmpty()){
                                        System.out.println("There is no member in this subreddit!\n");
                                    } else {
                                        System.out.println("Choose the number of a member(press 0 to go back):");
                                        for (int i = 0; i < subreddit.getMembers().size(); i++) {
                                            System.out.println(i+1 + ") u/" + subreddit.getMembers().get(i).getID());
                                        }
                                        Scanner input4 = new Scanner(System.in);
                                        int mem = input4.nextInt();
                                        if (mem != 0){
                                            Account member = subreddit.getMembers().get(mem-1);
                                            subreddit.setAdmins(member);
                                        }
                                    }
                                } else if (num.equals("Back")) {
                                    break;
                                } else if (num.isEmpty()) {

                                } else {
                                    char p = num.charAt(0);
                                    Posts post = subreddit.getPosts().get(p-49);
                                    postShow(post, user);
                                }
                            }
                        }
                    }
                    break;
                case "Posts":
                    while (true) {
                        if (user.postsData().isEmpty()) {
                            System.out.println("\nYou have not posted yet!");
                        } else {
                            System.out.println("\nPosts: ");
                            for (int i = 0; i < user.postsData().size(); i++) {
                                if (user.postsData().get(i).getSubreddit() == null) {
                                    System.out.println(i + 1 + ")" + "u/" + user.postsData().get(i).getUser().getID() +
                                            " posted by u/" + user.postsData().get(i).getUser().getID());
                                } else {
                                    System.out.println(i + 1 + ")" + "s/" + user.postsData().get(i).getSubreddit().getName() +
                                            " posted by u/" + user.postsData().get(i).getUser().getID());
                                }
                                System.out.println("Title: " + user.postsData().get(i).getTitle());
                                System.out.println("***\n" + user.postsData().get(i).getText() + "\n***");
                                System.out.println("Votes: " + user.postsData().get(i).getVote());
                            }
                            System.out.println("\n[if you want to open a post enter the number or enter 0 to go back to the profile]");
                            Scanner input3 = new Scanner(System.in);
                            int open = input3.nextInt();
                            if (open != 0) {
                                Posts post = user.postsData().get(open - 1);
                                postShow(post, user);
                            } else {
                                break;
                            }
                        }
                    }
                    break;
                case "Comments":
                    while (true) {
                        if (user.getComments().isEmpty()) {
                            System.out.println("\nYou have not left a comment yet!");
                        } else {
                            System.out.println("\nComments: ");
                            for (int i = 0; i < user.getComments().size(); i++) {
                                if (user.getComments().get(i).getPost().getSubreddit() == null) {
                                    System.out.print(i + 1 + ")" + "u/" + user.getComments().get(i).getUser().getID());
                                } else {
                                    System.out.print("s/" + user.getComments().get(i).getPost().getSubreddit().getName());
                                }
                                System.out.println("  \"" + user.getComments().get(i).getPost().getTitle() + "\"");
                                System.out.println(user.getComments().get(i).getDetail());
                                System.out.println("Votes: " + user.getComments().get(i).getVote());
                            }
                            System.out.println("\n[if you want to open a post enter the number or enter 0 to go back to the profile]");
                            Scanner input3 = new Scanner(System.in);
                            int open = input3.nextInt();
                            if (open != 0) {
                                Comment comment = user.getComments().get(open - 1);
                                votingComment(user, comment);
                            } else {
                                break;
                            }
                        }
                    }
                    break;
                case "Back":
                    break pro;
                default:
                    System.out.println("You entered wrong phrase!\n");
            }
        }
    }
    public static void changeEmail(Account user){
        System.out.println("Your Email is: " + user.getEmail());
        System.out.println("Enter your new Email :");
        Scanner input = new Scanner(System.in);
        String newEmail = input.nextLine();
        if (checkEmail(newEmail)) {
            user.setEmail(newEmail);
        } else {
            System.out.println("Invalid Email address!");
        }
    }
    public static void changeName(Account user){
        System.out.println("Your Name is : " + user.getName());
        System.out.println("Enter your new Name :");
        Scanner input = new Scanner(System.in);
        String newName = input.nextLine();
        user.setName(newName);
    }
    public static void changePass(Account user){
        System.out.println("Enter your Password: ");
        Scanner input1 = new Scanner(System.in);
        String oldPass = input1.nextLine();
        if (user.checkPassword(oldPass)) {
            System.out.println("Enter your new Password: ");
            Scanner input = new Scanner(System.in);
            String newPass = input.nextLine();
            user.setPassword(newPass);
        }
    }
    public static List<Posts> timeLine(Account user){
        List<Posts> timelinePosts = new ArrayList<>();
        int max = 0;
        for (int i = 0; i < user.subsData().size(); i++){
            if (user.subsData().get(i).getPosts() != null) {
                if (max < user.subsData().get(i).getPosts().size()) {
                    max = user.subsData().get(i).getPosts().size();
                }
            }
        }
        for (int k = 0; k < max; k++) {
            for (int i = 0; i < user.subsData().size(); i++) {
                int j = user.subsData().get(i).getPosts().size() - k;
                if (j > 0) {
                    timelinePosts.add(user.subsData().get(i).getPosts().get(j-1));
                }
            }
        }
        return timelinePosts;
    }
    public static void postShow(Posts post, Account user){
        if (post.getSubreddit() == null){
            System.out.println("u/" + post.getUser().getID() +
                    " posted by u/" + post.getUser().getID());
        } else {
            System.out.println("s/" + post.getSubreddit().getName() +
                    " posted by u/" + post.getUser().getID());
        }
        System.out.println("Title : " + post.getTitle());
        System.out.println("***\n" + post.getText() + "\n***");
        System.out.println("\nVotes : " + post.getVote() + "\n");
        if (post.comments != null && !(post.comments.isEmpty())) {
            System.out.println("\nComments :");
            for (int i = 0; i < post.comments.size(); i++) {
                System.out.print(i + 1 + ")");
                post.comments.get(i).show();
                System.out.println();
            }
        }
        System.out.println("\n[press 'V' if you want to vote the post OR press 'C' if you want to add a comment " +
                "OR press the number of the comment to vote it OR press 0 to go back]");
        Scanner input2 = new Scanner(System.in);
        char mov = input2.next().charAt(0);
        if (mov == 'V') {
            while (true) {
                Scanner input3 = new Scanner(System.in);
                System.out.println("1.upVote\t2.downVote");
                int vote = input3.nextInt();
                boolean check = false;
                UUID savekey = null;
                boolean preVote = false;
                for (UUID key : user.votes.keySet()){
                    if (post.getID() == key){
                        check = true;
                        savekey = key;
                        preVote = user.votes.get(key);
                    }
                }
                if (check) {
                    System.out.println("You vote this post before!");
                    System.out.println("Do you want to remove your vote?\n1.Yes\t2.No");
                    Scanner input4 = new Scanner(System.in);
                    int remove = input4.nextInt();
                    if (remove == 1) {
                        user.votes.remove(savekey);
                        if (preVote) {
                            post.downVote();
                            post.getUser().minusKarma("P");
                        } else {
                            post.upVote();
                            post.getUser().plusKarma("P");
                        }
                        break;
                    } else if (remove == 2) {
                        break;
                    } else {
                        System.out.println("You entered a wrong number!\n");
                    }
                } else {
                    if (vote == 1) {
                        user.upVotePost(post);
                        break;
                    } else if (vote == 2) {
                        user.downVotePost(post);
                        break;
                    } else {
                        System.out.println("You entered a wrong number!\n");
                    }
                }
            }
        } else if (mov == 'C') {
            user.addComment(post,user);
        } else if (mov == '0'){

        } else {
            Comment comment = post.comments.get(mov - 49);
            votingComment(user,comment);
        }
    }
    public static void votingComment(Account user, Comment comment){
        while (true) {
            Scanner input3 = new Scanner(System.in);
            System.out.println("1.upVote\t2.downVote");
            int vote = input3.nextInt();
            boolean check = false;
            UUID savekey = null;
            boolean preVote = false;
            for (UUID key : user.votes.keySet()){
                if (comment.getID() == key){
                    check = true;
                    savekey = key;
                    preVote = user.votes.get(key);
                }
            }
            if (check) {
                System.out.println("You vote this comment before!");
                System.out.println("Do you want to remove your vote?\n1.Yes\t2.No");
                Scanner input4 = new Scanner(System.in);
                int remove = input4.nextInt();
                if (remove == 1) {
                    user.votes.remove(savekey);
                    if (preVote) {
                        comment.downVote();
                        comment.getUser().minusKarma("C");
                    } else {
                        comment.upVote();
                        comment.getUser().plusKarma("C");
                    }
                    break;
                } else if (remove == 2) {
                    break;
                } else {
                    System.out.println("You entered a wrong number!\n");
                }
            } else {
                if (vote == 1) {
                    user.upVoteComment(comment);
                    break;
                } else if (vote == 2) {
                    user.downVoteComment(comment);
                    break;
                } else {
                    System.out.println("You entered a wrong number!\n");
                }
            }
        }
    }
    public static List<Account> searchUser(String search){
        List<Account> userFind = new ArrayList<>();

        String regex = ".*" + search + ".*";
        Pattern pattern = Pattern.compile(regex);
        for (int i = 0; i < users.size(); i++) {
            Matcher matcher = pattern.matcher(users.get(i).getID());
            if (matcher.find()) {
                userFind.add(users.get(i));
            }
        }
        return userFind;
    }
    public static List<Subreddit> searchSub(String search){
        List<Subreddit> subFind = new ArrayList<>();

        String regex = ".*" + search + ".*";
        Pattern pattern = Pattern.compile(regex);
        for (int i = 0; i < subreddits.size(); i++) {
            Matcher matcher = pattern.matcher(subreddits.get(i).getName());
            if (matcher.find()) {
                subFind.add(subreddits.get(i));
            }
        }
        return subFind;
    }
    public static void search(String search, Account user1){
        List<Account> matchUser = searchUser(search);
        List<Subreddit> matchSub = searchSub(search);
        System.out.println("\nUsers:");
        if (matchUser.isEmpty()){
            System.out.println("Nothing found!\n");
        } else {
            for (int i = 0; i < matchUser.size(); i++) {
                System.out.println((i + 1) + ") u/" + matchUser.get(i).getID());
            }
        }
        System.out.println("\nSubreddits:");
        if (matchSub.isEmpty()){
            System.out.println("Nothing found!\n");
        } else {
            for (int i = matchUser.size(); i < matchUser.size() + matchSub.size(); i++) {
                System.out.println((i + 1) + ") s/" + matchSub.get(i - matchUser.size()).getName() + "\n"
                        + matchSub.get(i - matchUser.size()).getNumOfMembers() + " Members\n");
            }
        }
        Scanner input = new Scanner(System.in);
        System.out.println("Enter the number of user/subreddit you want to view the page of(press 0 to go back to the menu):");
        int choose = input.nextInt();
        if (choose == 0) {

        } else if (choose > 0 && choose <= matchUser.size()) {
            while (true) {
                Account user = matchUser.get(choose - 1);
                System.out.println(user.getName());
                System.out.println("u/" + user.getID());
                System.out.println("\nPosts: ");
                if (user.postsData().isEmpty()) {
                    System.out.println("This user has not posted yet!\n");
                } else {
                    for (int i = 0; i < user.postsData().size(); i++) {
                        if (user.postsData().get(i).getSubreddit() != null) {
                            System.out.println((i + 1) + ")" + "s/" + user.postsData().get(i).getSubreddit().getName());
                        }
                        System.out.println("Title : " + user.postsData().get(i).getTitle());
                        System.out.println("***\n" + user.postsData().get(i).getText() + "\n***");
                    }
                }
                System.out.println("\nComments: ");
                if (user.getComments().isEmpty()) {
                    System.out.println("This user has not commented yet!\n");
                } else {
                    for (int i = 0; i < user.getComments().size(); i++) {
                        Comment comment = user.getComments().get(i);
                        System.out.println((i + 1) + ")" + "s/" + comment.getPost().getSubreddit() + "  \'" + comment.getPost().getTitle() + "\'");
                        System.out.println(comment.getDetail());
                    }
                }
                System.out.println("[Press 'P' for posts OR press 'C' for comments OR press '0' to go back]");
                Scanner input1 = new Scanner(System.in);
                char mov = input1.next().charAt(0);
                if (mov == 'P') {
                    System.out.println("Enter the number of the post you want to see:");
                    Scanner input2 = new Scanner(System.in);
                    int num = input2.nextInt();
                    Posts post = user.postsData().get(num - 1);
                    postShow(post, user1);
                } else if (mov == 'C') {
                    System.out.println("Enter the number of the comment you want to see:");
                    Scanner input2 = new Scanner(System.in);
                    int num = input2.nextInt();
                    Comment comment = user.getComments().get(num - 1);
                    System.out.println("s/" + comment.getPost().getSubreddit() + "  \'" + comment.getPost().getTitle() + "\'");
                    System.out.println(comment.getDetail());
                    System.out.println("0.back\t1.UpVote\t2.DownVote");
                    int vote = input2.nextInt();
                    boolean check = false;
                    UUID savekey = null;
                    boolean preVote = false;
                    for (UUID key : user.votes.keySet()) {
                        if (comment.getID() == key) {
                            check = true;
                            savekey = key;
                            preVote = user.votes.get(key);
                        }
                    }
                    if (check) {
                        System.out.println("You vote this comment before!");
                        System.out.println("Do you want to remove your vote?\n1.Yes\t2.No");
                        Scanner input4 = new Scanner(System.in);
                        int remove = input4.nextInt();
                        if (remove == 1) {
                            user.votes.remove(savekey);
                            if (preVote) {
                                comment.downVote();
                                comment.getUser().minusKarma("C");
                            } else {
                                comment.upVote();
                                comment.getUser().plusKarma("C");
                            }
                        } else if (remove == 2) {
                        }
                    } else {
                        if (vote == 1) {
                            user.upVoteComment(comment);
                        } else if (vote == 2) {
                            user.downVoteComment(comment);
                        }
                    }
                } else {
                    break;
                }
            }
        } else {
            while (true) {
                Subreddit sub = matchSub.get(choose - matchUser.size() - 1);
                System.out.println("s/" + sub.getName() + "\n");
                System.out.println("\nPosts: ");
                for (int i = 0; i < sub.getPosts().size(); i++) {
                    System.out.println((i + 1) + ")" + "Posted by u/" + sub.getPosts().get(i).getUserName());
                    System.out.println("Title : " + sub.getPosts().get(i).getTitle());
                    System.out.println("***\n" + sub.getPosts().get(i).getText() + "\n***");
                }
                System.out.println("Enter the number to see the post - Post - Back");
                Scanner input2 = new Scanner(System.in);
                String num = input2.next();
                if (num.equals("Post")) {
                    if (sub.checkAdmin(user1)) {
                        user1.createPost(user1, sub);
                    } else {
                        System.out.println("You are not allowed to post in this subreddit.\n");
                    }
                } else if (num.equals("Back")) {
                    break;
                } else {
                    char p = num.charAt(0);
                    Posts post = sub.getPosts().get(p - 49);
                    postShow(post, user1);
                }
            }
        }
    }
}
