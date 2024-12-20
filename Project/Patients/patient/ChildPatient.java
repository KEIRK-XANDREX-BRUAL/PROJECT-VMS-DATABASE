package patient;

import java.util.Scanner;
import vaccine.VaccineRecord;

public class ChildPatient extends PatientDetails {
    private String parentName;

    public ChildPatient(String patientID, String firstName, String lastName,String contactNumber, int age, String parentName) {
        super(patientID, firstName, lastName,contactNumber, age);
        this.parentName = parentName;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName){
        this.parentName = parentName;
    }

    public void displayPatientInfo() {
        System.out.println("Patient ID: " + patientID);
        System.out.println("Name: " + firstName + lastName);
        System.out.println("Contact Number: " + contactNumber);
        System.out.println("Age: " + age);
        System.out.println("Parent Name: " + parentName);
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