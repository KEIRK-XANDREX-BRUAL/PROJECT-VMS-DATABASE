package Database.data;

import java.sql.*;

public class VMSDatabase {
    private static final String URL = "jdbc:mysql://localhost:3306/";
    private static final String DBNAME = "VaccineManagementSystem";
    private static final String USER = "root";
    private static final String PASSWORD = "lamentis";

    public static Connection getConnection() {
        try {
            return attemptConnection(URL + DBNAME, USER, PASSWORD);
        } catch (SQLException e) {
            System.out.println("Error: Unable to connect to the database. Initializing setup...");
            return null;
        }
    }

    private static Connection attemptConnection(String fullUrl, String user, String password) throws SQLException {
        try {
            Connection conn = DriverManager.getConnection(fullUrl, user, password);
            try (Statement stmt = conn.createStatement()) {
                stmt.execute("USE " + DBNAME);
            }
            return conn;
        } catch (SQLException e) {
            if (e.getErrorCode() == 1045) {
                System.out.println("Error: Incorrect MySQL username or password.");
                throw e;
            } else if (fullUrl.equals(URL + DBNAME)) {
                System.out.println("Database not found. Initializing setup...");
                createDatabaseAndTables(user, password);
            } else {
                throw e;
            }
        }
        return DriverManager.getConnection(fullUrl, user, password);
    }

    private static void createDatabaseAndTables(String user, String password) {
        try (Connection conn = DriverManager.getConnection(URL, user, password);
             Statement stmt = conn.createStatement()) {

            stmt.execute("CREATE DATABASE IF NOT EXISTS " + DBNAME);
            System.out.println("Database '" + DBNAME + "' created (or already exists).");

            try (Connection dbConn = DriverManager.getConnection(URL + DBNAME, user, password);
                 Statement dbStmt = dbConn.createStatement()) {

                dbStmt.execute("CREATE TABLE IF NOT EXISTS Vaccine (" +
                        "vaccine_id INT AUTO_INCREMENT PRIMARY KEY, " +
                        "vaccine_name VARCHAR(255) NOT NULL, " +
                        "dosage INT, " +
                        "dose Interval DATE, " );

                dbStmt.execute("CREATE TABLE IF NOT EXISTS Patients (" +
                        "patientID VARCHAR(20) PRIMARY KEY, " +
                        "firstName VARCHAR(50), " +
                        "lastName VARCHAR(50), " +
                        "contactNumber VARCHAR(15), " +
                        "age INT, " +
                        "additionalInfo TEXT)");

                dbStmt.execute("CREATE TABLE IF NOT EXISTS VaccineRecords (" +
                        "recordID VARCHAR(20) PRIMARY KEY, " +
                        "patientID VARCHAR(20), " +
                        "vaccine_id INT, " +
                        "dateAdministered DATE, " +
                        "dosesAdministered INT, " +
                        "status ENUM('Completed', 'Not Completed'), " +
                        "FOREIGN KEY (patientID) REFERENCES Patients(patientID), " +
                        "FOREIGN KEY (vaccine_id) REFERENCES Vaccine(vaccine_id))");

                System.out.println("Tables created successfully!");
                System.out.println("Sample data inserted successfully!");

            }
        } catch (SQLException e) {
            System.out.println("Error: Unable to create the database or tables.");
            e.printStackTrace();
        }
    }
}
