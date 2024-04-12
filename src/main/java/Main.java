import java.util.List;
import java.util.Scanner;
import java.io.*;

public class Main {
    public static void main(String[] args) throws Exception{
        enterWeb();

    }
    public static void saveFile() throws Exception{
        ObjectOutputStream outUser = new ObjectOutputStream(new FileOutputStream("Account.txt"));
        outUser.writeObject(Info.users);
        outUser.close();
        ObjectOutputStream outSub = new ObjectOutputStream(new FileOutputStream("Subreddit.txt"));
        outSub.writeObject(Info.subreddits);
        outSub.close();
        ObjectOutputStream outPost = new ObjectOutputStream(new FileOutputStream("Post.txt"));
        outPost.writeObject(Info.posts);
        outPost.close();
    }
    public static void readFile() throws Exception{
        String fileName1 = "Account.txt";
        File file1 = new File(fileName1);
        if (file1.exists()) {
            ObjectInputStream inUser = new ObjectInputStream(new FileInputStream("Account.txt"));
            Info.users = (List<Account>) inUser.readObject();
            inUser.close();
        }
        String fileName2 = "Subreddit.txt";
        File file2 = new File(fileName2);
        if (file2.exists()) {
            ObjectInputStream inSub = new ObjectInputStream(new FileInputStream("Subreddit.txt"));
            Info.subreddits = (List<Subreddit>) inSub.readObject();
            inSub.close();
        }
        String fileName3 = "Post.txt";
        File file3 = new File(fileName3);
        if (file3.exists()) {
            ObjectInputStream inPost = new ObjectInputStream(new FileInputStream("Post.txt"));
            Info.posts = (List<Posts>) inPost.readObject();
            inPost.close();
        }
    }
    public static void enterWeb() throws Exception{
        Scanner input = new Scanner(System.in);
        System.out.println("\n\t\t\tHello Dear**\n\t\t  welcome to Reddit.\n");
        readFile();
        while (true) {
            System.out.println("Do you have an account?\n1.yes(log in)\t2.No(sign up)\t3.Exit\n");
            int enter = input.nextInt();
            if (enter == 2) {
                signup :
                while (true) {
                    System.out.println("\n[Enter \"BACK\" to go back to the first page]\n");
                    Scanner input1 = new Scanner(System.in);
                    System.out.println("Email:");
                    String email = input1.nextLine();
                    if (email.equals("BACK") || email.equals("back") || email.equals("Back")){
                        break;
                    }
                    if (Info.checkEmail(email)) {
                        if (!(Info.findEmail(email))) {
                            while (true) {
                                System.out.println("\n[Enter \"BACK\" to go back to the first page]\n");
                                System.out.println("Password(at least 8 characters):");
                                String pass = input.next();
                                if (pass.equals("BACK") || pass.equals("back") || pass.equals("Back")) {
                                    break signup;
                                }
                                if (Info.checkPass(pass)) {
                                    while (true) {
                                        System.out.println("\n[Enter \"BACK\" to go back to the first page]\n");
                                        System.out.println("Enter a name for your reddit account:");
                                        String name = input1.nextLine();
                                        if (name.equals("BACK") || name.equals("back") || name.equals("Back")) {
                                            break signup;
                                        }
                                        if (!name.isEmpty()) {
                                            while (true) {
                                                System.out.println("\n[Enter \"BACK\" to go back to the first page]\n");
                                                System.out.println("set a username for your reddit account(you can't change it again!):" +
                                                        "\n[at least 4 character & have at least one letter]");
                                                String username = input1.nextLine();
                                                if (username.equals("BACK") || username.equals("back") || username.equals("Back")) {
                                                    break signup;
                                                }
                                                if (Info.checkID(username)) {
                                                    Account account = new Account(name, pass, email);
                                                    if (account.setID(username)) {
                                                        Info.users.add(account);
                                                        break signup;
                                                    }
                                                } else {
                                                    System.out.println("Invalid Username!");
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    System.out.println("Invalid Password!");
                                }
                            }
                        } else {
                            System.out.println("This email has already been used!");
                        }
                    } else {
                        System.out.println("Invalid email!");
                    }
                }
            } else if (enter == 1) {
                login:
                while (true) {
                    Scanner input1 = new Scanner(System.in);
                    System.out.println("How do you want to enter reddit?\n1.With Email   2.With Username   3.Back");
                    int way = input1.nextInt();
                    if (way == 1) {
                        while (true) {
                            Scanner input2 = new Scanner(System.in);
                            System.out.println("\n[Enter \"BACK\" to go back to the first page]\n");
                            System.out.println("Email:");
                            String email = input2.nextLine();
                            if (email.equals("BACK") || email.equals("back") || email.equals("Back")) {
                                break;
                            }
                            if (Info.findEmail(email)) {
                                while (true) {
                                    System.out.println("Password:");
                                    String pass = input2.nextLine();
                                    for (int i = 0; i < Info.users.size(); i++) {
                                        if (email.equals(Info.users.get(i).getEmail())) {
                                            if (Info.users.get(i).checkPassword(pass)) {
                                                System.out.println("Correct password!\n");
                                                menu(Info.users.get(i));
                                                break login;
                                            } else {
                                                System.out.println("You entered a wrong password!\n");
                                                break;
                                            }
                                        }
                                    }
                                }
                            } else {
                                System.out.println("No registration has been made with this email!\n");
                            }
                        }
                    } else if (way == 2) {
                        while (true) {
                            Scanner input2 = new Scanner(System.in);
                            System.out.println("\n[Enter \"BACK\" to go back to the first page]\n");
                            System.out.println("Username:");
                            String username = input2.nextLine();
                            if (username.equals("BACK") || username.equals("back") || username.equals("Back")) {
                                break;
                            }
                            if (Info.findUser(username)) {
                                while (true) {
                                    System.out.println("Password:");
                                    String pass = input2.nextLine();
                                    for (int i = 0; i < Info.users.size(); i++) {
                                        if (username.equals(Info.users.get(i).getID())) {
                                            if (Info.users.get(i).checkPassword(pass)) {
                                                System.out.println("Correct password!\n");
                                                menu(Info.users.get(i));
                                                break login;
                                            } else {
                                                System.out.println("You entered a wrong password!\n");
                                                break;
                                            }
                                        }
                                    }
                                }
                            } else {
                                System.out.println("No registration has been made with this username!\n");
                            }
                        }
                    } else if (way == 3) {
                        break;
                    } else {
                        System.out.println("You entered a wrong number!\n");
                    }
                }
            } else if (enter == 3){
                saveFile();
                break;
            } else {
                System.out.println("You entered a wrong number!\n");
            }
        }
    }
    public static void menu(Account user){
        while (true) {
            Scanner input = new Scanner(System.in);
            System.out.println("What do you want to do?\n1.Timeline   2.Search   3.create a Post   4.join a subreddit  " +
                    " 5.create a Subreddit   6.Profile   7.Setting   8.log out");
            int choose = input.nextInt();
            if (choose == 1) {
                while (true) {
                    List<Posts> posts = Info.timeLine(user);
                    System.out.println("\nEnter the number of the post if you want to see it(press 0 to go back to the menu)\n");
                    for (int i = 0; i < posts.size(); i++) {
                        if (posts.get(i).getSubreddit() == null) {
                            System.out.println(i + 1 + ")" + "u/" + posts.get(i).getUser().getID() +
                                    " posted by u/" + posts.get(i).getUser().getID());
                        } else {
                            System.out.println(i + 1 + ")" + "s/" + posts.get(i).getSubreddit().getName() +
                                    " posted by u/" + posts.get(i).getUser().getID());
                        }
                        System.out.println("Title : " + posts.get(i).getTitle());
                        System.out.println("***\n" + posts.get(i).getText() + "\n***");
                    }
                    Scanner input1 = new Scanner(System.in);
                    int show = input1.nextInt();
                    if (show != 0) {
                        Posts post = posts.get(show - 1);
                        Info.postShow(post, user);
                    } else {
                        break;
                    }
                }
            } else if (choose == 2) {
                Scanner input1 = new Scanner(System.in);
                System.out.println("Enter the phrase you want to search:");
                String search = input1.nextLine();
                Info.search(search,user);
            } else if (choose == 3) {
                Scanner input1 = new Scanner(System.in);
                System.out.println("\n[Enter \"BACK\" to go back to the first page]\n");
                System.out.println("Select the subreddit you want to post from: ");
                System.out.println("0)Posting with my account not from a subreddit");
                for (int i = 0; i < user.getMySubreddits().size(); i++){
                    System.out.println((i+1) + ")" + user.getMySubreddits().get(i).getName());
                }
                String sub = input1.next();
                if (sub.equals("BACK") || sub.equals("back") || sub.equals("Back")){

                } else if (sub.equals("0")) {
                    user.createPost(user);
                } else {
                    char num = sub.charAt(0);
                    user.createPost(user,user.getMySubreddits().get(num-49));
                }
            } else if (choose == 4) {
                System.out.println("\n[Enter \"BACK\" to go back to the first page]\n");

                System.out.println("Enter the number of the subreddit you want to join: ");
                for (int i = 0; i < Info.subreddits.size(); i++){
                    System.out.println((i+1) + ")" + Info.subreddits.get(i).getName());
                }
                Scanner input1 = new Scanner(System.in);
                String num = input1.next();
                if (num.equals("BACK") || num.equals("back") || num.equals("Back")){

                } else {
                    char sub = num.charAt(0);
                    Subreddit subreddit = Info.subreddits.get(sub - 49);
                    boolean joinSub = false;
                    boolean mySub = false;
                    for (int i = 0; i < user.subsData().size(); i++) {
                        if (Info.subreddits.get(sub - 49) == user.subsData().get(i)) {
                            joinSub = true;
                            break;
                        }
                    }
                    for (int i = 0; i < user.getMySubreddits().size(); i++) {
                        if (Info.subreddits.get(sub - 49) == user.getMySubreddits().get(i)) {
                            mySub = true;
                            break;
                        }
                    }
                    if (mySub) {
                        System.out.println("You can't join to your subreddit:)\n");
                    } else if (joinSub) {
                        System.out.println("You already join in this subreddit!\n");
                    } else {
                        user.joinSubreddit(subreddit,user);
                    }
                }
            } else if (choose == 5) {
                System.out.println("\n[Enter \"BACK\" to go back to the first page]\n");
                user.createSubreddit(user);
            } else if (choose == 6) {
                Info.profile(user);
            } else if (choose == 7) {
                Scanner input1 = new Scanner(System.in);
                System.out.println("What do you want to do?\n1.change Email\t2.change Password\t3.change name");
                int change = input1.nextInt();
                if (change == 1){
                    Info.changeEmail(user);
                } else if (change == 2) {
                    Info.changePass(user);
                } else if (change == 3) {
                    Info.changeName(user);
                } else {
                    System.out.println("You entered a wrong number!\n");
                }
            } else if (choose == 8) {
                break;
            } else {
                System.out.println("You entered a wrong number!\n");
            }
        }
    }

}