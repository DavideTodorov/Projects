package user_account;

import java.util.Scanner;

public class UserAccountSettings  {

    //Method for changing UserName in UserAccount
    public static void changeUsername(Scanner scanner) {
        //Input
        System.out.println("Enter old username:");
        String oldUsername = scanner.nextLine();

        System.out.println("Enter new username:");
        String newUsername = scanner.nextLine();

        System.out.println("Enter password:");
        String password = scanner.nextLine();


        //Get UserAccount by the given input
        UserAccount userAccount = getUserAccount(oldUsername);


        //The correct password for current account is needed
        getCorrectPassword(scanner, password, userAccount);


        //Try to change password
        try {
            userAccount.setUsername(newUsername);
            System.out.println("Username changed successfully!");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }


    //Method for changing emails in UserAccount
    public static void changeEmail(Scanner scanner) {
        System.out.println("Enter username:");
        String username = scanner.nextLine();

        System.out.println("Enter new email:");
        String newEmail = scanner.nextLine();

        System.out.println("Enter password:");
        String password = scanner.nextLine();

        UserAccount userAccount = getUserAccount(username);

        getCorrectPassword(scanner, password, userAccount);

        try {
            userAccount.setEmail(newEmail);
            System.out.println("Email changed successfully!");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }


    //Method for changing password in UseAccount
    public static void changePassword(Scanner scanner) {
        System.out.println("Enter username:");
        String username = scanner.nextLine();

        System.out.println("Enter old password:");
        String oldPassword = scanner.nextLine();

        System.out.println("Enter new password:");
        String newPassword = scanner.nextLine();

        UserAccount userAccount = getUserAccount(username);

        getCorrectPassword(scanner, oldPassword, userAccount);

        try {
            userAccount.setPassword(newPassword);
            System.out.println("Password changed successfully!");
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
        }
    }


    //Get UserAccount from repository
    private static UserAccount getUserAccount(String username) {
        UserAccount userAccount = null;
        try {
            userAccount = UserAccountsRepository.getUserAccount(username);
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        }
        return userAccount;
    }

    //Method for validating the input password
    private static void getCorrectPassword(Scanner scanner, String password, UserAccount userAccount) {
        while (!userAccount.getPassword().equals(password)) {
            System.out.println("Wrong password! Try again!");
            System.out.println("Enter password:");
            password = scanner.nextLine();
        }
    }
}
