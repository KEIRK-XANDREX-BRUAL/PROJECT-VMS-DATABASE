package patient;

import java.util.Scanner;
import vaccine.VaccineRecord;

public class TeenPatient extends PatientDetails{
    private String schoolName;

    public TeenPatient(String patientID, String firstName, String lastName, String contactNumber, int age, String schoolName) {
        super(patientID, firstName, lastName, contactNumber, age);
        this.schoolName = schoolName;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName){
        this.schoolName = schoolName;
    }

    public void displayPatientInfo() {
        System.out.println("Patient ID: " + patientID);
        System.out.println("Name: " + firstName + lastName);
        System.out.println("Contact Number: " + contactNumber);
        System.out.println("Age: " + age);
        System.out.println("School Name: " + schoolName);
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