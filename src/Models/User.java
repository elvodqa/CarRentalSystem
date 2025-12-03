package Models;

public class User {
    public int  id;
    public String firstName;
    public String lastName;
    public String email;
    public String password;

    public User(int id, String firstName, String lastName, String email, String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }
}
