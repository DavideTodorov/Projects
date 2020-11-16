import user_account.UserAccountRegistrant;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        /*
        This is a program for registration and modification of user accounts.
        For now we use the console as UI.
        Every time we start the program, the first thing that needs to be done is to register the first account.
        Supported commands:
            1. registerAccount
            2. changeUsername
            3. changeEmail
            4. changePassword
            5. deleteAccount
        NOTE: When you choose a command the program will lead you how to use it!
         */

        UserAccountRegistrant.register(scanner);

        System.out.println();
    }
}
