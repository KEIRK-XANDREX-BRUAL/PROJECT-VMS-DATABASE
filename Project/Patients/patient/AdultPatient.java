package patient;

import java.util.Scanner;
import vaccine.VaccineRecord;

public class AdultPatient extends PatientDetails {
    private String occupation;

    public AdultPatient(String patientID, String firstName, String lastName, String contactNumber, int age, String occupation) {
        super(patientID, firstName, lastName, contactNumber, age);
        this.occupation = occupation;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation){
        this.occupation = occupation;
    }

    public void displayPatientInfo() {
        System.out.println("Patient ID: " + patientID);
        System.out.println("Name: " + firstName + lastName);
        System.out.println("Contact Number: " + contactNumber);
        System.out.println("Age: " + age);
        System.out.println("Occupation: " + occupation);
    }

    public void displayPatientRecords(){
        Scanner scanner = new Scanner(System.in);

        System.out.println("\t===== Vaccine Records ====");
        for (int i = 0; i < getVaccineRecords().size() ; i++) {
            VaccineRecord record = getVaccineRecords().get(i);
            record.displayRecordInfo();
        }
        System.out.println("\nPress Enter to return to the menu...");
        scanner.nextLine();  
        
        scanner.close();
    }
}
