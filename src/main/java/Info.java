import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Info {
    public static HashMap<String,Account> users;
    public static List<Subreddit> subreddits;
    public static List<Account> emails;
    public static List<Posts> posts;

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
        for (int i = 0; i < emails.size(); i++){
            if (email.equals(emails.get(i).getEmail())){
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
        for (String key : users.keySet()) {
            if (key == username){
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
        System.out.println("Your karma : " + user.getKarma("K") + "\n" + "Post's Karma : " + user.getKarma("P")
                + "Comment's Karma : " + user.getKarma("C"));
        System.out.println("Subreddit : \n" + user.getSubreddit());
        System.out.println("Posts : ");
        for (int i = 0; i < user.postsData().size(); i++) {
            if (user.postsData().get(i).getSubreddit() == null){
                System.out.println("u/" + user.postsData().get(i).getUserName() +
                        " posted by u/" + user.postsData().get(i).getUserName());
            } else {
                System.out.println("r/" + user.postsData().get(i).getSubreddit().getName() +
                        " posted by u/" + user.postsData().get(i).getUserName());
            }
            System.out.println(user.postsData().get(i).getTitle());
            System.out.println(user.postsData().get(i).getText());
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
        user.setName(newPass);
    }
}
