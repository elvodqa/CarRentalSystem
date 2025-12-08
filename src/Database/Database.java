package Database;

import Models.Car;
import Models.RentalPeriod;
import Models.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {
    public final String DbName = "RentalSystem";
    public final String UserTableName = "Users";
    public final String CarTableName = "Cars";
    public final String RentalPeriodTableName = "Rentals";

    private String connectionString = "jdbc:derby:" + DbName + ";create=true";
    private Connection conn = null;
    public Database() {
        connect();
    }

    private void connect() {
        try {
            conn = DriverManager.getConnection(connectionString);
            System.out.println("Connected to database successfully");
        } catch (SQLException e) {
            System.out.println("Error connecting to database: " + e.getMessage());
        }
    }

    public boolean createTables() {
        boolean result = true;

        // ------------------ User table ----------------------
        String userSql = "CREATE TABLE " + UserTableName + " (" +
                "id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY, " +
                "firstName VARCHAR(30), " +
                "lastName VARCHAR(30), " +
                "email VARCHAR(30), " +
                "password VARCHAR(30))";
        try (Statement statement = conn.createStatement()) {
            statement.executeUpdate(userSql);
            System.out.println("Table " + UserTableName + " created successfully.");
        } catch (SQLException e) {
            System.out.println("Error creating table: " + e.getMessage());
            result = false;
        }

        // ------------------ Car table ----------------------
        String carSql = "CREATE TABLE " + CarTableName + " (" +
                "id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY, " +
                "name VARCHAR(30), " +
                "model VARCHAR(30)," +
                "plate VARCHAR(30), " +
                "color VARCHAR(30), " +
                "price DOUBLE)";
        try (Statement statement = conn.createStatement()) {
            statement.executeUpdate(carSql);
            System.out.println("Table " + CarTableName + " created successfully.");
        } catch (SQLException e) {
            System.out.println("Error creating table: " + e.getMessage());
            result = false;
        }

        // ------------------ Rental Period table ----------------------
        String rentalSql = "CREATE TABLE " + RentalPeriodTableName + " (" +
                "id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY, " +
                "userId INT, " +
                "carId INT," +
                "startDate DATE, " +
                "endDate DATE) ";
        try (Statement statement = conn.createStatement()) {
            statement.executeUpdate(rentalSql);
            System.out.println("Table " + RentalPeriodTableName + " created successfully.");
        } catch (SQLException e) {
            System.out.println("Error creating table: " + e.getMessage());
            result = false;
        }

        return result;
    }


    // Returns true on success, false on failure
    public boolean createUser(String firstName, String lastName, String email, String password) {
        String insertSql = "INSERT INTO " + UserTableName +
                " (firstName, lastName, email, password) VALUES ('" +
                firstName + "', '" + lastName + "', '" + email + "', '" + password + "')";
        try (Statement statement = conn.createStatement()) {
            statement.executeUpdate(insertSql);
            System.out.println("New user added: " + firstName + " " + lastName);
            return true;
        } catch (SQLException e) {
            System.out.println("Error adding new user: " + e.getMessage());
            return false;
        }
    }

    // Returns true if user with given email and password are true, else false
    public boolean validateUser(String email, String password) {
        String querySql = "SELECT * FROM " + UserTableName +
                " WHERE email = '" + email + "' AND password = '" + password + "'";
        try (Statement statement = conn.createStatement();
             var resultSet = statement.executeQuery(querySql)) {
            if (resultSet.next()) {
                System.out.println("User validated: " + email);
                return true;
            } else {
                System.out.println("Invalid credentials for user: " + email);
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Error validating user: " + e.getMessage());
            return false;
        }
    }

    public boolean createCar(String name, String model, String plate, String color, double price) {
        String insertSql = "INSERT INTO " + CarTableName +
                " (name, model, plate, color, price) VALUES ('" +
                name + "', '" + model + "', '" + plate + "', '" + color + "', " + price + ")";
        try (Statement statement = conn.createStatement()) {
            statement.executeUpdate(insertSql);
            System.out.println("New car added: " + name + " " + model);
            return true;
        } catch (SQLException e) {
            System.out.println("Error adding new car: " + e.getMessage());
            return false;
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String querySql = "SELECT * FROM " + UserTableName;
        try (Statement statement = conn.createStatement();
             var resultSet = statement.executeQuery(querySql)) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                users.add(new User(id, firstName, lastName, email, password));
            }
            System.out.println("Retrieved all users from database.");
        } catch (SQLException e) {
            System.out.println("Error retrieving users: " + e.getMessage());
        }
        return users;
    }

    public User getUserById(int userId) {
        User user = null;
        String querySql = "SELECT * FROM " + UserTableName + " WHERE id = " + userId;
        try (Statement statement = conn.createStatement();
             var resultSet = statement.executeQuery(querySql)) {
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                user = new User(id, firstName, lastName, email, password);
            }
            System.out.println("Retrieved user with ID: " + userId);
        } catch (SQLException e) {
            System.out.println("Error retrieving user: " + e.getMessage());
        }
        return user;
    }

    public Car getCarById(int carId) {
        Car car = null;
        String querySql = "SELECT * FROM " + CarTableName + " WHERE id = " + carId;
        try (Statement statement = conn.createStatement();
             var resultSet = statement.executeQuery(querySql)) {
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String model = resultSet.getString("model");
                String plate = resultSet.getString("plate");
                String color = resultSet.getString("color");
                double price = resultSet.getDouble("price");
                car = new Car(id, name, model, plate, color, price);
            }
            System.out.println("Retrieved car with ID: " + carId);
        } catch (SQLException e) {
            System.out.println("Error retrieving car: " + e.getMessage());
        }
        return car;
    }

    public List<RentalPeriod> getAllRentalByUserId(int userId) {
        List<RentalPeriod> rentals = new ArrayList<>();
        String querySql = "SELECT * FROM " + RentalPeriodTableName + " WHERE userId = " + userId;
        try (Statement statement = conn.createStatement();
             var resultSet = statement.executeQuery(querySql)) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int carId = resultSet.getInt("carId");
                java.sql.Date startDate = resultSet.getDate("startDate");
                java.sql.Date endDate = resultSet.getDate("endDate");
                rentals.add(new RentalPeriod(id, userId, carId, startDate, endDate));
            }
            System.out.println("Retrieved all rental periods for user ID: " + userId);
        } catch (SQLException e) {
            System.out.println("Error retrieving rental periods: " + e.getMessage());
        }
        return rentals;
    }

    public RentalPeriod getRentalPeriodById(int rentalPeriodId) {
        RentalPeriod rentalPeriod = null;
        String querySql = "SELECT * FROM " + RentalPeriodTableName + " WHERE id = " + rentalPeriodId;
        try (Statement statement = conn.createStatement();
             var resultSet = statement.executeQuery(querySql)) {
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                int userId = resultSet.getInt("userId");
                int carId = resultSet.getInt("carId");
                java.sql.Date startDate = resultSet.getDate("startDate");
                java.sql.Date endDate = resultSet.getDate("endDate");
                rentalPeriod = new RentalPeriod(id, userId, carId, startDate, endDate);
            }
            System.out.println("Retrieved rental period with ID: " + rentalPeriodId);
        } catch (SQLException e) {
            System.out.println("Error retrieving rental period: " + e.getMessage());
        }
        return rentalPeriod;
    }

    public List<Car> getAllCars() {
        List<Car> cars = new ArrayList<>();
        String querySql = "SELECT * FROM " + CarTableName;
        try (Statement statement = conn.createStatement();
             var resultSet = statement.executeQuery(querySql)) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String model = resultSet.getString("model");
                String plate = resultSet.getString("plate");
                String color = resultSet.getString("color");
                double price = resultSet.getDouble("price");
                cars.add(new Car(id, name, model, plate, color, price));
            }
            System.out.println("Retrieved all cars from database.");
        } catch (SQLException e) {
            System.out.println("Error retrieving cars: " + e.getMessage());
        }
        return cars;
    }

    public boolean createRentalPeriod(int userId, int carId, Date startDate, Date endDate) {
        String overlapSql = "SELECT COUNT(*) FROM " + RentalPeriodTableName +
                " WHERE carId = ? AND NOT (endDate < ? OR startDate > ?)";
        try (PreparedStatement checkStmt = conn.prepareStatement(overlapSql)) {
            checkStmt.setInt(1, carId);
            checkStmt.setDate(2, startDate);
            checkStmt.setDate(3, endDate);
            try (ResultSet rs = checkStmt.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    System.out.println("Car ID " + carId + " is already rented in the given period.");
                    return false;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error checking rental overlap: " + e.getMessage());
            return false;
        }

        String insertSql = "INSERT INTO " + RentalPeriodTableName +
                " (userId, carId, startDate, endDate) VALUES (?, ?, ?, ?)";
        try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
            insertStmt.setInt(1, userId);
            insertStmt.setInt(2, carId);
            insertStmt.setDate(3, startDate);
            insertStmt.setDate(4, endDate);
            insertStmt.executeUpdate();
            System.out.println("New rental period added for user ID: " + userId + " and car ID: " + carId);
            return true;
        } catch (SQLException e) {
            System.out.println("Error adding new rental period: " + e.getMessage());
            return false;
        }
    }

    public String generateInvoice(int rentalId) {
        String stuffSql = "SELECT r.id, u.firstName, u.lastName, c.name, c.model, r.startDate, r.endDate, c.price " +
                "FROM " + RentalPeriodTableName + " r " +
                "JOIN " + UserTableName + " u ON r.userId = u.id " +
                "JOIN " + CarTableName + " c ON r.carId = c.id " +
                "WHERE r.id = " + rentalId;
        String invoice = "";
        try (Statement statement = conn.createStatement();
                var resultSet = statement.executeQuery(stuffSql)) {
            if (resultSet.next()) {
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                String carName = resultSet.getString("name");
                String carModel = resultSet.getString("model");
                String startDate = resultSet.getString("startDate");
                String endDate = resultSet.getString("endDate");
                double pricePerDay = resultSet.getDouble("price");
                // Calculate total days
                java.sql.Date start = java.sql.Date.valueOf(startDate);
                java.sql.Date end = java.sql.Date.valueOf(endDate);
                long diff = end.getTime() - start.getTime();
                int totalDays = (int) (diff / (1000 * 60 * 60 * 24)) + 1;
                double totalPrice = totalDays * pricePerDay;

                invoice += "Invoice for Rental ID: " + rentalId + "\n";
                invoice += "Customer Name: " + firstName + " " + lastName + "\n";
                invoice += "Car: " + carName + " " + carModel + "\n";
                invoice += "Rental Period: " + startDate + " to " + endDate + " (" + totalDays + " days)\n";
                invoice += "Price per Day: $" + pricePerDay + "\n";
                invoice += "Total Price: $" + totalPrice + "\n";
            } else {
                invoice = "No rental found with ID: " + rentalId;
            }
        } catch (SQLException e) {
            System.out.println("Error generating invoice: " + e.getMessage());
            invoice = "Error generating invoice.";
        }

        return invoice;
    }

    public List<RentalPeriod> getUserRentals(int userId) {
        List<RentalPeriod> rentals = new ArrayList<>();
        String querySql = "SELECT * FROM " + RentalPeriodTableName + " WHERE userId = " + userId;
        try (Statement statement = conn.createStatement();
             var resultSet = statement.executeQuery(querySql)) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int carId = resultSet.getInt("carId");
                java.sql.Date startDate = resultSet.getDate("startDate");
                java.sql.Date endDate = resultSet.getDate("endDate");
                rentals.add(new RentalPeriod(id, userId, carId, startDate, endDate));
            }
            System.out.println("Retrieved rentals for user ID: " + userId);
        } catch (SQLException e) {
            System.out.println("Error retrieving user rentals: " + e.getMessage());
        }
        return rentals;
    }

    public void close() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
                System.out.println("Database connection closed.");
            }
        } catch (SQLException e) {
            System.out.println("Error closing database connection: " + e.getMessage());
        }
    }

    public boolean cancelRental(int rentalId) {
        String deleteSql = "DELETE FROM " + RentalPeriodTableName + " WHERE id = " + rentalId;
        try (Statement statement = conn.createStatement()) {
            int rowsAffected = statement.executeUpdate(deleteSql);
            if (rowsAffected > 0) {
                System.out.println("Rental ID " + rentalId + " cancelled successfully.");
                return true;
            } else {
                System.out.println("No rental found with ID: " + rentalId);
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Error cancelling rental: " + e.getMessage());
            return false;
        }
    }

    public boolean banUser(int userId) {
        String deleteSql = "DELETE FROM " + UserTableName + " WHERE id = " + userId;
        try (Statement statement = conn.createStatement()) {
            int rowsAffected = statement.executeUpdate(deleteSql);
            if (rowsAffected > 0) {
                System.out.println("User ID " + userId + " banned successfully.");
                return true;
            } else {
                System.out.println("No user found with ID: " + userId);
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Error banning user: " + e.getMessage());
            return false;
        }
    }

    public boolean updateCarPrice(int carId, double newPrice) {
        String updateSql = "UPDATE " + CarTableName + " SET price = " + newPrice + " WHERE id = " + carId;
        try (Statement statement = conn.createStatement()) {
            int rowsAffected = statement.executeUpdate(updateSql);
            if (rowsAffected > 0) {
                System.out.println("Car ID " + carId + " price updated to $" + newPrice);
                return true;
            } else {
                System.out.println("No car found with ID: " + carId);
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Error updating car price: " + e.getMessage());
            return false;
        }
    }

    public boolean updateCarColor(int carId, String newColor) {
        String updateSql = "UPDATE " + CarTableName + " SET color = '" + newColor + "' WHERE id = " + carId;
        try (Statement statement = conn.createStatement()) {
            int rowsAffected = statement.executeUpdate(updateSql);
            if (rowsAffected > 0) {
                System.out.println("Car ID " + carId + " color updated to " + newColor);
                return true;
            } else {
                System.out.println("No car found with ID: " + carId);
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Error updating car color: " + e.getMessage());
            return false;
        }
    }

    public boolean updateCarName(int carId, String newName) {
        String updateSql = "UPDATE " + CarTableName + " SET name = '" + newName + "' WHERE id = " + carId;
        try (Statement statement = conn.createStatement()) {
            int rowsAffected = statement.executeUpdate(updateSql);
            if (rowsAffected > 0) {
                System.out.println("Car ID " + carId + " name updated to " + newName);
                return true;
            } else {
                System.out.println("No car found with ID: " + carId);
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Error updating car name: " + e.getMessage());
            return false;
        }
    }

    public boolean updateCarPlate(int carId, String newPlate) {
        String updateSql = "UPDATE " + CarTableName + " SET plate = '" + newPlate + "' WHERE id = " + carId;
        try (Statement statement = conn.createStatement()) {
            int rowsAffected = statement.executeUpdate(updateSql);
            if (rowsAffected > 0) {
                System.out.println("Car ID " + carId + " plate updated to " + newPlate);
                return true;
            } else {
                System.out.println("No car found with ID: " + carId);
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Error updating car plate: " + e.getMessage());
            return false;
        }
    }
}
