package user_account;

import java.util.Scanner;

public interface Modifiable {

    void changeUsername(Scanner scanner);

    void changeEmail(Scanner scanner);

    void changePassword(Scanner scanner);
}
