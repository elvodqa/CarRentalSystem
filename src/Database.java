import Models.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    public final String DbName = "RentalSystem";
    public final String UserTableName = "Users";
    public final String CarTableName = "Cars";

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
            return false;
        }

        // ------------------ Car table ----------------------
        String carSql = "CREATE TABLE " + CarTableName + " (" +
                "id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY, " +
                "name VARCHAR(30), " +
                "model VARCHAR(30)," +
                "plate VARCHAR(30), " +
                "color VARCHAR(30), " +
                "name DOUBLE)";
        try (Statement statement = conn.createStatement()) {
            statement.executeUpdate(carSql);
            System.out.println("Table " + CarTableName + " created successfully.");
        } catch (SQLException e) {
            System.out.println("Error creating table: " + e.getMessage());
            return false;
        }

        return true;
    }


}
