package vaccine;


import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

public class Vaccines {
    private String vaccineName;
    private LocalDate nextDoseDate;
    private int requiredDoses;
    private double doseVolume;

    private static List<Vaccines> availableVaccine = new ArrayList<>();

    public Vaccines(String vaccineName, LocalDate nextDoseDate, int requiredDoses, double doseVolume) {
        this.vaccineName = vaccineName;
        this.nextDoseDate = nextDoseDate;
        this.requiredDoses = requiredDoses;
        this.doseVolume = doseVolume;
    }

    public static void vaccineVault() {
        LocalDate today = LocalDate.now();
        if (availableVaccine.isEmpty()) {
            availableVaccine.add(new Vaccines("Influenza", today.plusYears(1), 1, 0.5));
            availableVaccine.add(new Vaccines("Chickenpox", today.plusMonths(4), 2, 0.5));
            availableVaccine.add(new Vaccines("Tetanus", today.plusYears(10), 1, 0.5));
            availableVaccine.add(new Vaccines("Polio", today.plusMonths(5), 3, 0.5));
            availableVaccine.add(new Vaccines("Hepatitis B", today.plusMonths(3), 3, 1));
        }
    }

    public static List<Vaccines> getVaccineVault() {
        return availableVaccine;
    }

    public String getVaccineName() {
        return vaccineName;
    }

    public LocalDate getNextDoseDate(){
        return nextDoseDate;
    }

    public int getRequiredDoses(){
        return requiredDoses;
    }

    public double getDoseVolume(){
        return doseVolume;
    }

    public static void displayVaccineVault() {
        System.out.println("\nAvailable Vaccines:");
        for (int i = 0; i < availableVaccine.size(); i++) {
            Vaccines vaccine = availableVaccine.get(i);
            System.out.println((i + 1) + ". Vaccine Name: " + vaccine.getVaccineName());
            System.out.println("\t  Next Vaccination date: " + vaccine.getNextDoseDate());
            System.out.println("\t   Required Doses: " + vaccine.getRequiredDoses());
            System.out.println("\t   Dose Volume per shot: " + vaccine.getDoseVolume());
            System.out.println();
        }
    }
}
