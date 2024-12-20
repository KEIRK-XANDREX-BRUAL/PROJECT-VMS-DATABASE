package Database.data;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import patient.AdultPatient;
import patient.ChildPatient;
import patient.PatientDetails;
import patient.TeenPatient;

public class PatientDAO {
    
    private static final String INSERT_PATIENT_QUERY = "INSERT INTO Patients (patientID, firstName, lastName, contactNumber, age, additionalInfo) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String SELECT_ALL_PATIENTS_QUERY = "SELECT * FROM Patients";

    public void addPatient(PatientDetails patient, String additionalInfo) throws SQLException {
        try (Connection conn = VMSDatabase.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(INSERT_PATIENT_QUERY)) {

            pstmt.setString(1, patient.getPatientID());
            pstmt.setString(2, patient.getFirstName());
            pstmt.setString(3, patient.getLastName());
            pstmt.setString(4, patient.getContactNumber());
            pstmt.setInt(5, patient.getAge());
            pstmt.setString(6, additionalInfo);

            pstmt.executeUpdate();
        }
    }

    public List<PatientDetails> getAllPatients() throws SQLException {
        List<PatientDetails> patients = new ArrayList<>();

        try (Connection conn = VMSDatabase.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SELECT_ALL_PATIENTS_QUERY)) {

            while (rs.next()) {
                String patientID = rs.getString("patientID");
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");
                String contactNumber = rs.getString("contactNumber");
                int age = rs.getInt("age");
                String additionalInfo = rs.getString("additionalInfo");

                if (age <= 12) {
                    patients.add(new ChildPatient(patientID, firstName, lastName, contactNumber, age, additionalInfo));
                } else if (age > 12 && age < 21) {  
                    patients.add(new TeenPatient(patientID, firstName, lastName, contactNumber, age, additionalInfo));
                } else {
                    patients.add(new AdultPatient(patientID, firstName, lastName, contactNumber, age, additionalInfo));
                }
            }
        }

        return patients;
    }

    public PatientDetails getPatientById(String patientID) throws SQLException {
        System.out.println("Searching for patient ID: " + patientID); 
    
        String SELECT_PATIENT_BY_ID_QUERY = "SELECT * FROM Patients WHERE patientID = ?";
    
        try (Connection conn = VMSDatabase.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SELECT_PATIENT_BY_ID_QUERY)) {
    
            pstmt.setString(1, patientID);
    
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String firstName = rs.getString("firstName");
                    String lastName = rs.getString("lastName");
                    String contactNumber = rs.getString("contactNumber");
                    int age = rs.getInt("age");
                    String additionalInfo = rs.getString("additionalInfo");
    
                    System.out.println("Patient found: " + firstName + " " + lastName);
    
                    if (age <= 12) {
                        return new ChildPatient(patientID, firstName, lastName, contactNumber, age, additionalInfo);
                    } else if (age > 12 && age < 21) {
                        return new TeenPatient(patientID, firstName, lastName, contactNumber, age, additionalInfo);
                    } else {
                        return new AdultPatient(patientID, firstName, lastName, contactNumber, age, additionalInfo);
                    }
                } else {
                    System.out.println("No patient found with ID: " + patientID);
                }
            }
        }
    
        return null;
    }
    
    }
