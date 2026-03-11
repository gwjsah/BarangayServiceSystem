public class Account {

    private String accountId;
    private String username;
    private String password;
    private String role;
    private boolean isActive;

    public Account(String accountId, String username, String password, String role) {
        this.accountId = accountId;
        this.username = username;
        this.password = password;
        this.role = role;
        this.isActive = true;
    }

    public boolean login(String username, String password) {
        if (this.username.equals(username) && this.password.equals(password)) {
            System.out.println("Login successful. Welcome, " + username + "!");
            return true;
        } else {
            System.out.println("Invalid username or password.");
            return false;
        }
    }

    public void logout() {
        System.out.println("Account " + username + " has been logged out.");
    }

    public void deactivate() {
        this.isActive = false;
        System.out.println("Account " + username + " has been deactivated.");
    }

    public void displayInfo() {
        System.out.println("Account ID: " + accountId);
        System.out.println("Username: " + username);
        System.out.println("Role: " + role);
        System.out.println("Active: " + isActive);
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
