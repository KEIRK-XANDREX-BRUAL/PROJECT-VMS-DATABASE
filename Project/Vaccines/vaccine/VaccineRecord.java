package vaccine;

import java.time.LocalDate;

enum VaccineStatus { 
    COMPLETED,
    NOT_COMPLETED
}

public class VaccineRecord {
    private Vaccines vaccine;
    private LocalDate dateAdministered;
    private int dosesAdministered;
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
        if (this.dosesAdministered < vaccine.getRequiredDoses()) {
            this.dosesAdministered++;
            this.dateAdministered = LocalDate.now();
            if (this.dosesAdministered == vaccine.getRequiredDoses()) {
                this.status = VaccineStatus.COMPLETED; // Mark as completed when all doses are given
            }
        } else {
            System.out.println("All required doses for " + vaccine.getVaccineName() + " have already been administered.");
        }
    }

    public void displayRecordInfo() {
        System.out.println("Vaccine: " + vaccine.getVaccineName());
        System.out.println("Date Administered: " + (dateAdministered != null ? dateAdministered : "Not administered"));
        System.out.println("Administration Status: " + status); // Updated to use enum
    }
}