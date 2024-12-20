package Database.data;

import java.sql.*;

public class VaccinesDAO {

    private static final String CREATE_TABLE_QUERY = 
        "CREATE TABLE IF NOT EXISTS vaccines (" +
        "vaccine_id VARCHAR(10) PRIMARY KEY, " +
        "vaccine_name VARCHAR(100) NOT NULL, " +
        "required_doses INT NOT NULL, " +
        "dose_volume DOUBLE NOT NULL, " +
        "interval_days INT NOT NULL);";

    private static final String INSERT_VACCINE_QUERY = 
        "INSERT INTO vaccines (vaccine_id, vaccine_name, required_doses, dose_volume, interval_days) " +
        "VALUES (?, ?, ?, ?, ?);";

    public static void createVaccinesTableIfNotExists() {
        try (Connection conn = VMSDatabase.getConnection(); 
             Statement stmt = conn.createStatement()) {
            stmt.execute(CREATE_TABLE_QUERY);
            System.out.println("Vaccines table checked/created successfully.");
        } catch (SQLException e) {
            System.out.println("Error creating vaccines table: " + e.getMessage());
        }
    }

    public static void insertVaccine(String vaccineId, String vaccineName, int requiredDoses, double doseVolume, int intervalDays) {
        try (Connection conn = VMSDatabase.getConnection(); 
             PreparedStatement pstmt = conn.prepareStatement(INSERT_VACCINE_QUERY)) {
            pstmt.setString(1, vaccineId);
            pstmt.setString(2, vaccineName);
            pstmt.setInt(3, requiredDoses);
            pstmt.setDouble(4, doseVolume);
            pstmt.setInt(5, intervalDays);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error inserting vaccine into database: " + e.getMessage());
        }
    }
}
