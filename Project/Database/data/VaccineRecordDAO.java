package Database.data;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import patient.PatientDetails;
import vaccine.VaccineRecord;


public class VaccineRecordDAO {

    public String generateRecordID() throws SQLException {
        String query = "SELECT MAX(CAST(SUBSTRING(recordID, 8) AS UNSIGNED)) FROM VaccineRecords";
        try (Connection conn = VMSDatabase.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                int maxID = rs.getInt(1);
                return "RECORD-" + String.format("%03d", maxID + 1);
            }
        }
        return "RECORD-001";
    }

    public void addVaccineRecord(VaccineRecord record, String patientID) throws SQLException {
        String query = "INSERT INTO VaccineRecords (recordID, patientID, vaccine_id, dateAdministered, dosesAdministered, status) " +
                       "VALUES (?, ?, ?, ?, ?, ?) " +
                       "ON DUPLICATE KEY UPDATE dosesAdministered = VALUES(dosesAdministered), status = VALUES(status), dateAdministered = VALUES(dateAdministered)";
    
        try (Connection conn = VMSDatabase.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
    
            String recordID = generateRecordID(); 
            pstmt.setString(1, recordID);
            pstmt.setString(2, patientID);  
            pstmt.setString(3, record.getVaccine().getVaccineId()); 
            pstmt.setDate(4, Date.valueOf(record.getDateAdministered()));   
            pstmt.setInt(5, record.getDosesAdministered());    
            pstmt.setString(6, record.getStatus().toString());  
    
            pstmt.executeUpdate();  
        } catch (SQLException e) {
            System.out.println("Error inserting vaccine record: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    
        public List<VaccineRecord> getVaccineRecordsByPatientId(String patientId) {
            List<VaccineRecord> records = new ArrayList<>();
            String query = "SELECT * FROM VaccineRecords WHERE patientID = ?";
    
            try (Connection conn = VMSDatabase.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, patientId);
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    String recordID = rs.getString("recordID");
                    String vaccineId = rs.getString("vaccine_id");
                    LocalDate dateAdministered = rs.getDate("dateAdministered").toLocalDate();
                    int dosesAdministered = rs.getInt("dosesAdministered");
                    String status = rs.getString("status");
    
                    VaccineRecord record = new VaccineRecord(recordID, patientId, vaccineId, dateAdministered, dosesAdministered, status);
                    records.add(record);
                }
            } catch (SQLException e) {
                System.out.println("Error retrieving vaccine records: " + e.getMessage());
            }
            return records;
        }

            private static final String CREATE_VACCINE_RECORDS_TABLE_QUERY = 
        "CREATE TABLE IF NOT EXISTS VaccineRecords (" +
        "recordID VARCHAR(15) PRIMARY KEY, " +
        "patientID VARCHAR(10), " +
        "vaccine_id VARCHAR(10), " +
        "dateAdministered DATE, " +
        "dosesAdministered INT, " +
        "status VARCHAR(20), " +
        "FOREIGN KEY (patientID) REFERENCES Patients(patientID), " +
        "FOREIGN KEY (vaccine_id) REFERENCES vaccines(vaccine_id));";

    public static void createVaccineRecordsTableIfNotExists() {
        try (Connection conn = VMSDatabase.getConnection(); 
            Statement stmt = conn.createStatement()) {
            stmt.execute(CREATE_VACCINE_RECORDS_TABLE_QUERY);
            System.out.println("VaccineRecords table checked/created successfully.");
        } catch (SQLException e) {
            System.out.println("Error creating VaccineRecords table: " + e.getMessage());
        }
    }

}
