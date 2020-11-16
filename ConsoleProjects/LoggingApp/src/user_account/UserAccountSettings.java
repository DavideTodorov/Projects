package user_account;

import java.util.Scanner;

public class UserAccountSettings implements Modifiable {

    @Override
    public void changeUsername(Scanner scanner) {
        System.out.println("Enter old username:");
        String oldUsername = scanner.nextLine();

        System.out.println("Enter new username:");
        String newUsername = scanner.nextLine();

        System.out.println("Enter password:");
        String password = scanner.nextLine();

        UserAccount userAccount = null;
        try {
            userAccount = UserAccountsRepository.getUserAccount(oldUsername);
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        }


        while (!userAccount.getPassword().equals(password)) {
            System.out.println("Wrong password! Try again!");
            System.out.println("Enter password:");
            password = scanner.nextLine();
        }

        try {
            userAccount.setUsername(newUsername);
            System.out.println("Username changed successfully!");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void changeEmail(Scanner scanner) {
    }

    @Override
    public void changePassword(Scanner scanner) {
    }
}
