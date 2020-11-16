package user_account;

import java.awt.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class UserAccountsRepository {
    protected static Map<String, UserAccount> repository = new HashMap<>();


    protected static void addUserAccount(UserAccount account) {
        if (repository.containsKey(account.getUserName())) {
            throw new IllegalStateException(String.format("\"%s\" username is already registered!",
                    account.getUserName()));
        }

        repository.put(account.getUserName(), account);
    }

    protected static UserAccount getUserAccount(String username) {
        if (!repository.containsKey(username)) {
            throw new IllegalStateException(String.format("\"%s\" username does not exist!",
                    username));
        }

        return repository.get(username);
    }
}
