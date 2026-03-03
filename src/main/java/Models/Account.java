package Models;

public abstract class Account {
    private String email;
    private String id;

    public Account() {

    }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }
}
