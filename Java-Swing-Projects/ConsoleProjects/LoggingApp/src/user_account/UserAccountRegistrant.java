package user_account;

import java.util.Scanner;

public class UserAccountRegistrant extends UserAccountsRepository {

    public static void register(Scanner scanner) {
        System.out.println("Register user:");

        while (true) {
            System.out.println("Enter name:");
            String name = scanner.nextLine();

            System.out.println("Enter email:");
            String email = scanner.nextLine();

            System.out.println("Enter password:");
            String password = scanner.nextLine();

            try {
                UserAccount userAccount = new UserAccount
                        (name, email, password);


                UserAccountRegistrant.addUserAccount(userAccount);

                System.out.printf("User \"%s\" created successfully!%n", name);
                return;
            } catch (IllegalArgumentException | IllegalStateException e) {
                System.out.println(e.getMessage());
                System.out.println("Try again!");
            }
        }
    }
}
