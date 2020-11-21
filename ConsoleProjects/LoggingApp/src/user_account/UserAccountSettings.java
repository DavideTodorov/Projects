package user_account;

import java.util.Scanner;

public class UserAccountSettings implements Modifiable {

    //Method for changing UserName in UserAccount
    @Override
    public void changeUsername(Scanner scanner) {
        //Input
        System.out.println("Enter old username:");
        String oldUsername = scanner.nextLine();

        System.out.println("Enter new username:");
        String newUsername = scanner.nextLine();

        System.out.println("Enter password:");
        String password = scanner.nextLine();


        //Get UserAccount by the given input
        UserAccount userAccount = null;
        try {
            userAccount = UserAccountsRepository.getUserAccount(oldUsername);
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        }


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

    @Override
    public void changeEmail(Scanner scanner) {
    }

    //Method for changing password in UseAccount

    @Override
    public void changePassword(Scanner scanner) {
    }


    //Method for validating the input password
    private void getCorrectPassword(Scanner scanner, String password, UserAccount userAccount) {
        while (!userAccount.getPassword().equals(password)) {
            System.out.println("Wrong password! Try again!");
            System.out.println("Enter password:");
            password = scanner.nextLine();
        }
    }
}
