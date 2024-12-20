package vaccine;

import java.time.LocalDate;


public class VaccineRecord {
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

        if (vaccine.getIntervalDays() > 0 && dosesAdministered < vaccine.getRequiredDoses()) {
            this.nextVaccinationDate = dateAdministered.plusDays(vaccine.getIntervalDays());
        } else {
            this.nextVaccinationDate = null; 
            this.status = VaccineStatus.COMPLETED; 
        }
    }

    public LocalDate getNextVaccinationDate() {
        return nextVaccinationDate;
    }

    public void displayRecordInfo() {
        System.out.println("Vaccine ID: " + vaccine.getVaccineId());
        System.out.println("Vaccine: " + vaccine.getVaccineName());
        System.out.println("Date Administered: " + (dateAdministered != null ? dateAdministered : "Not administered"));
        System.out.println("Administration Status: " + status);
    }
}