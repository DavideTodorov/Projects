package user_account;

import java.util.Scanner;

public interface Modifiable {

    String changeUsername(Scanner scanner);

    String changeEmail(Scanner scanner);

    String changePassword(Scanner scanner);
}
