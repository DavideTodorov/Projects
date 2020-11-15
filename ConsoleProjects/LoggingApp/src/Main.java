import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

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
                System.out.printf("User %s created!%n", name);
                break;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                System.out.println("Try again!");
            }
        }
    }
}
