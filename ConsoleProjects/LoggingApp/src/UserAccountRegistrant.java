import java.util.Scanner;

public class UserAccountRegistrant {

    public static String register(Scanner scanner) {
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

                try {
                    UserAccountsRepository.addUserAccount(userAccount);

                    return String.format("User %s created successfully!%n", name);
                } catch (IllegalStateException ex) {
                    System.out.println(ex.getMessage());
                    System.out.println("Try again!");
                }
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                System.out.println("Try again!");
            }
        }
    }
}
