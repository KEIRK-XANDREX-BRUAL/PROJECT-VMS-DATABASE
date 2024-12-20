package vaccine;

import java.time.LocalDate;


public class VaccineRecord {

    private String recordId;
    private String patientId;
    private String vaccineId;
    private Vaccines vaccine;
    private LocalDate dateAdministered;
    private int dosesAdministered;
    private LocalDate nextVaccinationDate;
    private VaccineStatus status; 

    public VaccineRecord(Vaccines vaccine) {
        this.vaccine = vaccine;
        this.dateAdministered = null;
        this.dosesAdministered = 0;
        this.status = VaccineStatus.NOT_COMPLETED; 
    }
    public VaccineRecord(String recordId, String patientId, String vaccineId, LocalDate dateAdministered, int dosesAdministered, String status) {
        this.recordId = recordId;
        this.patientId = patientId;
        this.vaccineId = vaccineId;
        this.vaccine = new Vaccines(vaccineId, dosesAdministered, dosesAdministered, dosesAdministered, vaccineId);
        this.dateAdministered = dateAdministered;
        this.dosesAdministered = dosesAdministered;
        this.status = VaccineStatus.valueOf(status);
        this.nextVaccinationDate = (status.equals("COMPLETED")) ? null : dateAdministered.plusDays(vaccine.getIntervalDays());
    }

    public String getRecordId() {
        return recordId;
    }

    public String getPatientId() {
        return patientId;
    }

    public String getVaccineId() {
        return vaccineId;
    }

    public Vaccines getVaccine() {
        return vaccine;
    }

    public LocalDate getDateAdministered() {
        return dateAdministered;
    }

    public VaccineStatus getStatus() { 
        return status;
    }

    public int getDosesAdministered(){
        return dosesAdministered;
    }

    public void administerDose() {
        this.dateAdministered = LocalDate.now();
        this.dosesAdministered++;
    
        if (dosesAdministered >= vaccine.getRequiredDoses()) {
            this.status = VaccineStatus.COMPLETED;
            this.nextVaccinationDate = null;
        } else {
            this.nextVaccinationDate = dateAdministered.plusDays(vaccine.getIntervalDays());
        }
    }

    public LocalDate getNextVaccinationDate() {
        return nextVaccinationDate;
    }

    public void displayRecordInfo() {
        System.out.println("Record ID: " + recordId);
        System.out.println("Patient ID: " + patientId);
        System.out.println("Vaccine ID: " + vaccineId);
        System.out.println("Date Administered: " + dateAdministered);
        System.out.println("Doses Administered: " + dosesAdministered);
        System.out.println("Status: " + status);
    }
}