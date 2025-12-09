import Database.Database;
import Models.User;

public class Test {
    public static void main(String[] args) {
        Database db = new Database();
        db.createTables();
        //db.createUser("John", "Doe", "johndoe@gmail.com", "password123");
        //db.createUser("Jane", "Smith", "janesmith@gmail.com", "securepass");

        System.out.println("Users created successfully.");
        for (User userInfo : db.getAllUsers()) {
            System.out.println(userInfo);
        }

        db.getAllRentals();

        db.getRentalPeriodsByCarId(1);



    }
}
