package Models;

public class User {
    public int  id;
    public String firstName;
    public String lastName;
    public String email;
    public String password;

    public User()
    {
        this.id = 0;
        this.firstName = "";
        this.lastName = "";
        this.email = "";
        this.password = "";
    }

    public User(int id, String firstName, String lastName, String email, String password)
    {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;

    }

    public void setUser(int id, String firstName, String lastName, String email, String password)
    {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;

    }

    public void getUser()
    {
        System.out.println("User ID: " + this.id);
        System.out.println("First Name: " + this.firstName);
        System.out.println("Last Name: " + this.lastName);
        System.out.println("Email: " + this.email);
        System.out.println("Password: " + this.password);
    }

    public String toString() {
        return id + " " + firstName + " " + lastName + " " + email + " " + password;
    }
}
