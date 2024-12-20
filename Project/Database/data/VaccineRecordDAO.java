package Database.data;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


import java.time.LocalDate;

import patient.PatientDetails;
import vaccine.VaccineRecord;
import vaccine.Vaccines;
import vaccine.VaccineStatus;


public class VaccineRecordDAO {

    private static final String INSERT_RECORD_QUERY = 
    "INSERT INTO VaccineRecords (recordID, patientID, vaccineName, vaccineId, dateAdministered, dosesAdministered, status) VALUES (?, ?, ?, ?, ?, ?, ?)";

    private static final String SELECT_RECORDS_BY_PATIENT_ID_QUERY = 
    "SELECT * FROM VaccineRecords WHERE patientID = ?";

    public void addVaccineRecord(VaccineRecord record, String patientID) throws SQLException {
        try (Connection conn = VMSDatabase.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(INSERT_RECORD_QUERY)) {

            pstmt.setString(1, "RECORD-" + System.nanoTime());
            pstmt.setString(2, patientID);
            pstmt.setString(3, record.getVaccine().getVaccineName());
            pstmt.setString(4, record.getVaccine().getVaccineId());
            pstmt.setDate(5, record.getDateAdministered() != null ? Date.valueOf(record.getDateAdministered()) : null);
            pstmt.setInt(6, record.getDosesAdministered());
            pstmt.setString(7, record.getStatus().toString());

            pstmt.executeUpdate();
        }
    }

    public List<VaccineRecord> getVaccineRecordsByPatientId(String patientID) throws SQLException {
        List<VaccineRecord> records = new ArrayList<>();
        try (Connection conn = VMSDatabase.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(SELECT_RECORDS_BY_PATIENT_ID_QUERY)) {

            pstmt.setString(1, patientID);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String vaccineName = rs.getString("vaccineName");
                    String vaccineId = rs.getString("vaccineId");
                    Date sqlDate = rs.getDate("dateAdministered");
                    LocalDate dateAdministered = sqlDate != null ? sqlDate.toLocalDate() : null;

                    int dosesAdministered = rs.getInt("dosesAdministered");
                    String statusString = rs.getString("status");
                    VaccineStatus status = statusString != null ? VaccineStatus.valueOf(statusString) : VaccineStatus.NOT_COMPLETED;

                    List<Vaccines> vaccineVault = Vaccines.getVaccineVault();
                    if (vaccineVault != null) {
                        Vaccines vaccine = vaccineVault.stream()
                                .filter(v -> v.getVaccineId().equals(vaccineId)) // Match by vaccineId
                                .findFirst()
                                .orElse(null);
                        if (vaccine != null) {
                            VaccineRecord record = new VaccineRecord(vaccine);
                            for (int i = 0; i < dosesAdministered; i++) {
                                record.administerDose();
                            }
                            records.add(record);
                        }
                    }
                }
            }
        }
        return records;
    }
}
