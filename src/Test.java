import Database.Database;
import Models.User;
import Models.Car;

public class Test {
    public static void main(String[] args) {
        Database db = new Database();
        db.createTables();
        db.createUser("John", "Doe", "johndoe@gmail.com", "password123");
        db.createUser("Jane", "Smith", "janesmith@gmail.com", "securepass");
        db.createUser("A", "R", "arminhappy2@gmail.com", "123");
        db.createCar("Forester", "2018", "FSI1123", "Grey", 500);
        db.createCar("Explorer", "2024", "SAC0312", "Red", 1000);

        System.out.println("Users created successfully.");
        for (User userInfo : db.getAllUsers()) {
            System.out.println(userInfo);
        }

        for (Car carInfo : db.getAllCars())
        {
            System.out.println(carInfo);
        }


    }
}
