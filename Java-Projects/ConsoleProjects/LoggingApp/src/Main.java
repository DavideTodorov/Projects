import user_account.UserAccountRegistrant;
import user_account.UserAccountSettings;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        /*
        This is a program for registration and modification of user accounts.
        For now we use the console as UI.
        Every time we start the program, the first thing that needs to be done is to register the first account.
        Supported commands(case sensitive):
            1. registerAccount
            2. changeUsername
            3. changeEmail
            4. changePassword
            5. deleteAccount
        NOTE: When you choose a command the program will lead you how to use it!
        The programme will run until "End" is entered as a command!
         */

        UserAccountRegistrant.register(scanner);

        System.out.println("Enter command:");
        String input = scanner.nextLine();
        while (!"End".equalsIgnoreCase(input)) {
            
            if (input.equals("registerAccount")){
                UserAccountRegistrant.register(scanner);
                input = scanner.nextLine();
                continue;
            }


            Class<UserAccountSettings> clazz = UserAccountSettings.class;

            try {
                Method method = clazz.getDeclaredMethod(input, Scanner.class);
                method.invoke(null, new Scanner(System.in));
            } catch (NoSuchMethodException
                    | IllegalAccessException
                    | InvocationTargetException e) {
                e.printStackTrace();
            }


            System.out.println("Enter command:");
            input = scanner.nextLine();
        }
    }
}
