import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Info {
//    public static HashMap<String,Account> users;
    public static List<Subreddit> subreddits = new ArrayList<>();
    public static List<Account> users = new ArrayList<>();
    public static List<Posts> posts = new ArrayList<>();


    public static boolean checkEmail(String email){
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
        System.out.println("Username : " + user.getID());
        System.out.println("Email : " + user.getEmail());
        System.out.println("Name : " + user.getName());
        System.out.println("\nYour karma : " + user.getKarma("K") + "\n" + "Post's Karma : " + user.getKarma("P") + "\n"
                + "Comment's Karma : " + user.getKarma("C"));
        System.out.println("\nSubreddit you join: \n" + user.getSubreddit() + "\nSubreddits you create: \n" + user.getMySubredditName());
        System.out.println("\nPosts : ");
        for (int i = 0; i < user.postsData().size(); i++) {
            if (user.postsData().get(i).getSubreddit() == null){
                System.out.println("u/" + user.postsData().get(i).getUser().getID() +
                        " posted by u/" + user.postsData().get(i).getUser().getID());
            } else {
                System.out.println("s/" + user.postsData().get(i).getSubreddit().getName() +
                        " posted by u/" + user.postsData().get(i).getUser().getID());
            }
            System.out.println("Title : " + user.postsData().get(i).getTitle());
            System.out.println("***\n" + user.postsData().get(i).getText() + "\n***");
        }
    }
    public static void changeEmail(Account user){
        System.out.println("Your Email is : " + user.getEmail());
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
            System.out.println("Your Password is : " + user.getPassword());
        System.out.println("Enter your new Password :");
        Scanner input = new Scanner(System.in);
        String newPass = input.nextLine();
        user.setPassword(newPass);
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
        System.out.println("\nVotes : " + post.getVote());
        if (post.comments != null) {
            System.out.println("\nComments :\n");
            for (int i = 0; i < post.comments.size(); i++) {
                System.out.print(i + 1 + ")");
                post.comments.get(i).show();
                System.out.println();
            }
        }
        System.out.println("[press 'V' if you want to vote the post OR press 'C' if you want to add a comment " +
                "OR press the number of the comment to vote it OR press 0 to go back to the timeline]");
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
                    System.out.println("You vote this post before!");
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
    public static void search(String search){
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
                System.out.println((i + 1) + ") s/" + matchSub.get(i - matchUser.size()).getName() + "\n" + matchSub.get(i - matchUser.size()).getNumOfMembers() + " Members\n");
            }
        }
        Scanner input = new Scanner(System.in);
        System.out.println("Enter the number of user/subreddit you want to view the page of(press 0 to go back to the menu):");
        int choose = input.nextInt();
        if (choose == 0) {

        } else if (choose > 0 && choose <= matchUser.size()) {
            Account user = matchUser.get(choose - 1);
            System.out.println(user.getName());
            System.out.println("u/" + user.getID());
            System.out.println("\nPosts: ");
            if (user.postsData().isEmpty()) {
                System.out.println("This user has not posted yet!\n");
            } else {
                for (int i = 0; i < user.postsData().size(); i++) {
                    if (user.postsData().get(i).getSubreddit() != null) {
                        System.out.println(i+1 + "s/" + user.postsData().get(i).getSubreddit().getName());
                    }
                    System.out.println("Title : " + user.postsData().get(i).getTitle());
                    System.out.println("***\n" + user.postsData().get(i).getText() + "\n***");
                }
            }
            System.out.println("\nComments: ");
            if (user.getComments().isEmpty()){
                System.out.println("This user has not commented yet!\n");
            } else {
                for (int i = 0; i < user.getComments().size(); i++) {
                    Comment comment = user.getComments().get(i);
                    System.out.println(i+1 + "s/" + comment.getPost().getSubreddit() + "  \'" + comment.getPost().getTitle() + "\'");
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
                Posts post = user.postsData().get(num-1);
                postShow(post,user);
            } else if (mov == 'C') {
                System.out.println("Enter the number of the comment you want to see:");
                Scanner input2 = new Scanner(System.in);
                int num = input2.nextInt();
                Comment comment = user.getComments().get(num-1);
                System.out.println("s/" + comment.getPost().getSubreddit() + "  \'" + comment.getPost().getTitle() + "\'");
                System.out.println(comment.getDetail());
                System.out.println("0.back\t1.UpVote\t2.DownVote");
                int vote = input2.nextInt();
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

            }
        } else {
            Subreddit sub = matchSub.get(choose - matchUser.size() - 1);
            System.out.println("s/" + sub.getName() + "\n");
            System.out.println("\nPosts: ");
            for (int i = 0; i < sub.getPosts().size(); i++){
                System.out.println(i+1 + "Posted by u/" + sub.getPosts().get(i).getUserName());
                System.out.println("Title : " + sub.getPosts().get(i).getTitle());
                System.out.println("***\n" + sub.getPosts().get(i).getText() + "\n***");
            }
            System.out.println("Enter the number of the post you want to see(press 0 if you want to go back to the menu):");
            Scanner input2 = new Scanner(System.in);
            int num = input2.nextInt();
            if (num == 0){

            } else {
                Posts post = sub.getPosts().get(num - 1);
                postShow(post, post.getUser());
            }
        }
    }
}
