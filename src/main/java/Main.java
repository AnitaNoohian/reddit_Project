import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        enterWeb();

    }
    public static void enterWeb() {
        Scanner input = new Scanner(System.in);
        System.out.println("Hello Dear**\nwelcome to Reddit.");
        while (true) {
            System.out.println("Do you have an account?\n1.yes(log in)\t2.No(sign up)\t3.Exit\n");
            int enter = input.nextInt();
            if (enter == 2) {
                signup :
                while (true) {
                    Scanner input1 = new Scanner(System.in);
                    System.out.println("Email:");
                    String email = input1.nextLine();
                    if (Info.checkEmail(email)) {
                        if (Info.findEmail(email)) {
                            System.out.println("Password:");
                            int pass = input.nextInt();
                            System.out.println("Enter a name for your reddit account:");
                            String name = input1.nextLine();
                            System.out.println("set a username for your reddit account(you can't change it again!):");
                            while (true) {
                                String username = input1.nextLine();
                                Account account = new Account(name);
                                if (account.setID(username)) {
                                    Info.users.put(account.getID(),account);
                                    Info.emails.add(account);
                                    break signup;
                                }
                            }
                        } else {
                            System.out.println("This email has already been used!\n");
                        }
                    } else {
                        System.out.println("Invalid email!\n");
                    }
                }
            } else if (enter == 1) {
                Scanner input1 = new Scanner(System.in);
                System.out.println("How do you want to enter reddit?\n1.With Email\t2.With Username");
                int way = input1.nextInt();
                if (way == 1){
                    login :
                    while (true) {
                        Scanner input2 = new Scanner(System.in);
                        System.out.println("Email:");
                        String email = input2.nextLine();
                        if (Info.findEmail(email)) {
                            while (true) {
                                System.out.println("Password:");
                                String pass = input2.nextLine();
                                for (int i = 0; i < Info.emails.size(); i++) {
                                    if (email.equals(Info.emails.get(i).getEmail())) {
                                        if (pass.equals(Info.emails.get(i).getPassword())) {
                                            System.out.println("Correct password!\n");
                                        //    menu(Info.emails.get(i));
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
                } else if (way == 2){
                    login :
                    while (true) {
                        Scanner input2 = new Scanner(System.in);
                        System.out.println("Username:");
                        String username = input2.nextLine();
                        if (Info.findUser(username)) {
                            while (true) {
                                System.out.println("Password:");
                                String pass = input2.nextLine();
                                for (String key : Info.users.keySet()) {
                                    if (username.equals(Info.users.get(key).getID())) {
                                        if (pass.equals(Info.users.get(key).getPassword())) {
                                            System.out.println("Correct password!\n");
                                           // menu(Info.users.get(key));
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
                } else if (way == 3){
                    break;
                } else {
                    System.out.println("You entered a wrong number!\n");
                }
            } else {
                System.out.println("You entered a wrong number!\n");
            }
        }
    }


}