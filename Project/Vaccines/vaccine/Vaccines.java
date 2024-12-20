package vaccine;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Vaccines {
    private String vaccineName;
    private int requiredDoses;
    private double doseVolume; 
    private int intervalDays; 
    private String vaccineId; 

    private static List<Vaccines> availableVaccine = new ArrayList<>();

    public Vaccines(String vaccineName, int requiredDoses, double doseVolume, int intervalDays, String vaccineId) {
        this.vaccineName = vaccineName;
        this.requiredDoses = requiredDoses;
        this.doseVolume = doseVolume;
        this.intervalDays = intervalDays;
        this.vaccineId = vaccineId;
    }

    public static void vaccineVault() {
        if (availableVaccine.isEmpty()) {
            availableVaccine.add(new Vaccines("Influenza", 1, 0.5, 365, "V001"));   // 1 year interval
            availableVaccine.add(new Vaccines("Chickenpox", 2, 0.5, 120, "V002")); // 4 months interval
            availableVaccine.add(new Vaccines("Tetanus", 1, 0.5, 1825, "V003"));   // 5 years interval
            availableVaccine.add(new Vaccines("Polio", 3, 0.5, 150, "V004"));      // 5 months interval
            availableVaccine.add(new Vaccines("Hepatitis B", 3, 1.0, 90, "V005")); // 3 months interval
        }
    }

    public static List<Vaccines> getVaccineVault() {
        return availableVaccine;
    }

    public String getVaccineName() {
        return vaccineName;
    }

    public int getRequiredDoses() {
        return requiredDoses;
    }

    public double getDoseVolume() {
        return doseVolume;
    }

    public int getIntervalDays() {
        return intervalDays;
    }

    public String getVaccineId() {
        return vaccineId;
    }

    public static void displayVaccineVault() {
        System.out.println("\nAvailable Vaccines:");
        for (int i = 0; i < availableVaccine.size(); i++) {
            Vaccines vaccine = availableVaccine.get(i);
            System.out.println((i + 1) + ". Vaccine ID: " + vaccine.getVaccineId());
            System.out.println("\t  Vaccine Name: " + vaccine.getVaccineName());
            System.out.println("\t  Required Doses: " + vaccine.getRequiredDoses());
            System.out.println("\t  Dose Volume per shot: " + vaccine.getDoseVolume() + " mL");
            System.out.println("\t  Interval between doses: " + vaccine.getIntervalDays() + " days");
            System.out.println();
        }
    }
    
    public LocalDate calculateNextDoseDate(LocalDate lastDoseDate, int dosesAdministered) {
        if (dosesAdministered < requiredDoses) {
            return lastDoseDate.plusDays(intervalDays);
        }
        return null;
    }
}
