package user_account;

public class UserAccount {

    private String userName;
    private String email;
    private String password;

    public UserAccount(String userName, String email, String password) {
        this.setUsername(userName);
        this.setEmail(email);
        this.setPassword(password);
    }

    //Validate password
    protected void setPassword(String password) {
        if (password.length() <= 4) {
            throw new IllegalArgumentException(
                    "Password should be at least 5 characters");
        }

        this.password = password;
    }

    //Validate email
    protected void setEmail(String email) {
        if (!email.contains("@")) {
            throw new IllegalArgumentException(
                    "Email should contain '@'");
        }

        this.email = email;
    }


    //Validate username
    protected void setUsername(String userName) {
        if (userName.length() <= 4 || userName.length() > 15) {
            throw new IllegalArgumentException(
                    "UserName should be between 4 and 15 characters");
        }

        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return String.format("Username: %s%n" +
                        "Email: %s%n" +
                        "Password: %s", this.userName,
                this.email,
                this.password.replaceAll("\\w", "*"));
    }
}
