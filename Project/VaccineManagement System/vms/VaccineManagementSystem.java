package vms;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import vaccine.Vaccines;
import vaccine.VaccineRecord;
import patient.PatientDetails;
import patient.ChildPatient;
import patient.TeenPatient;
import patient.AdultPatient;


public class VaccineManagementSystem {
    private List<PatientDetails> children;
    private List<PatientDetails> teens;
    private List<PatientDetails> adults;
    private int patientCounter;
    private Scanner scanner;
    private Utilities utilities;

    public VaccineManagementSystem() {
        this.children = new ArrayList<>();
        this.teens = new ArrayList<>();
        this.adults = new ArrayList<>();
        this.patientCounter = 1;
        this.scanner = new Scanner(System.in); 
        Vaccines.vaccineVault();
    }

    // Add a new patient
    public void addPatient() {
        System.out.print("Enter First Name: ");
        String firstName = scanner.nextLine();
        System.out.print("Enter Last Name: ");
        String lastName = scanner.nextLine();
        System.out.print("Enter contact number (11 digits): ");
        String contactNumber = scanner.nextLine();
        System.out.print("Enter Age: ");
        int age = scanner.nextInt();
        scanner.nextLine();

        String patientID = generatePatientID();
        if (age <= 12) {
            System.out.print("Enter Parent Name: ");
            String parentName = scanner.nextLine();
            children.add(new ChildPatient(patientID, firstName, lastName,contactNumber, age, parentName));
            System.out.println("Patient added to children category.");
        } else if (age > 12 && age < 21) {
            System.out.print("Enter School Name: ");
            String schoolName = scanner.nextLine();
            teens.add(new TeenPatient(patientID, firstName, lastName, contactNumber, age, schoolName));
            System.out.println("Patient added to teens category.");
        } else {
            System.out.print("Enter Occupation: ");
            String occupation = scanner.nextLine();
            adults.add(new AdultPatient(patientID, firstName, lastName, contactNumber, age, occupation)); 
            System.out.println("Patient added to adults category.");
        }
    }

    // Administer Vaccine
    public void administerVaccine(PatientDetails patient) {
        if (patient == null) {
            return;
        }
        System.out.println("Selected Patient: " + patient.getFirstName() + " " + patient.getLastName());
        Vaccines.displayVaccineVault();
    
        System.out.print("Select a vaccine number to administer: ");
        int choice = scanner.nextInt() - 1;
        scanner.nextLine();
    
        List<Vaccines> vaccineVault = Vaccines.getVaccineVault();
        if (choice >= 0 && choice < vaccineVault.size()) {
            Vaccines selectedVaccine = vaccineVault.get(choice);
    
            // Check if the patient already has the maximum required doses for this vaccine
            VaccineRecord patientRecord = patient.getVaccineRecord(selectedVaccine); // Assume this method exists to get the record
            if (patientRecord != null && patientRecord.getDosesAdministered() >= selectedVaccine.getRequiredDoses()) {
                System.out.println("This patient has already received all required doses for " + selectedVaccine.getVaccineName());
            } else {
                // If patient doesn't have the vaccine or hasn't completed all doses, administer the dose
                if (patientRecord == null) {
                    patientRecord = new VaccineRecord(selectedVaccine); // Create new record if no existing one
                }
                patientRecord.administerDose();
                patient.addVaccineRecord(patientRecord);
    
                System.out.println("Vaccine administered successfully to " + patient.getFirstName() + " " + patient.getLastName());
            }
        } else {
            System.out.println("Invalid vaccine selection.");
        }
    }

    // display patients
    public void displayAllPatients() {
        System.out.println("\n---- All Patients ----");

        for (int i = 0; i < children.size(); i++) {
            PatientDetails patient = children.get(i);
            System.out.println("Patient ID: " + patient.getPatientID() + ", Name: " + patient.getFirstName() + " " + patient.getLastName());
        }

        for (int i = 0; i < teens.size(); i++) {
            PatientDetails patient = teens.get(i);
            System.out.println("Patient ID: " + patient.getPatientID() + ", Name: " + patient.getFirstName() + " " + patient.getLastName());
        }

        for (int i = 0; i < adults.size(); i++) {
            PatientDetails patient = adults.get(i);
            System.out.println("Patient ID: " + patient.getPatientID() + ", Name: " + patient.getFirstName() + " " + patient.getLastName());
        }
    }
    
    //display patient's records
    public void patientRecords(PatientDetails patient){
        if (patient == null) {
            return;
        }
        patient.displayPatientInfo();
        System.out.println();
        patient.displayPatientRecords();


    }   

    // find patient by ID
    public PatientDetails findPatientByID(String patientID) {
        for (int i = 0; i < children.size(); i++) {
            if (children.get(i).getPatientID().equals(patientID)) {
                return children.get(i);
            }
        }

        for (int i = 0; i < teens.size(); i++) {
            if (teens.get(i).getPatientID().equals(patientID)) {
                return teens.get(i);
            }
        }

        for (int i = 0; i < adults.size(); i++) {
            if (adults.get(i).getPatientID().equals(patientID)) {
                return adults.get(i);
            }
        }

        return null;
    }

    //Patient's Menu
    public void patientMenu(PatientDetails patient) {
        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            System.out.println("\n\t\t==== Patient's Menu ====");
            System.out.println("\t[1] Administer Vaccine");
            System.out.println("\t[2] Display Patient's Info");
            System.out.println("\t[3] Edit Patient Info");
            System.out.println("\t[4] Back to main");
            System.out.print("\tEnter your choice: ");
            
            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input! Please enter a number.");
                scanner.next(); 
            }
            choice = scanner.nextInt();
            scanner.nextLine();  
            
            switch (choice) {
                case 1:
                    administerVaccine(patient);
                    break;
                case 2:
                    patientRecords(patient);
                    break;
                case 3:
                    utilities.delay(500);
                    editPatientInfo(patient);
                    utilities.clearScreen();
                    break;
                case 4:
                    System.out.println("Going back to main...");
                    utilities.delay(500);
                    utilities.clearScreen();
                    return;
                default:
                    System.out.println("Invalid choice. Please choose again.");
                    break;
            }
            scanner.close();
        } while (choice != 4);
    }
    
    //Edit Patient's Info
    public void editPatientInfo(PatientDetails patient) {
        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            System.out.println("\n\t\t ==== Edit Patient Info ====");
            System.out.println("\t [1] Edit Name");
            System.out.println("\t [2] Edit Contact Number");
            System.out.println("\t [3] Edit Age");
            
            // Display appropriate option based on patient type
            if (patient instanceof ChildPatient) {
                System.out.println("\t[4] Edit Parent Name");
            } else if (patient instanceof TeenPatient) {
                System.out.println("\t[4] Edit School Name");
            } else if (patient instanceof AdultPatient) {
                System.out.println("\t[4] Edit Occupation");
            }
            
            System.out.println("\t [5] Go back to Patient Menu");
            System.out.print("\tEnter your choice: ");
            
            // Check for valid input
            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input! Please enter a number.");
                scanner.next(); // Clear the invalid input
            }
            choice = scanner.nextInt();
            scanner.nextLine();  // Consume the newline left over from nextInt()
    
            switch (choice) {
                case 1:
                    System.out.print("Enter new first name: ");
                    patient.setFirstName(scanner.nextLine());
                    System.out.print("Enter new last name: ");
                    patient.setLastName(scanner.nextLine());
                    break;
                case 2:
                    System.out.print("Enter new Contact Number: ");
                    patient.setContactNumber(scanner.nextLine());
                    break;
                case 3:
                    System.out.print("Enter new Age: ");
                    while (!scanner.hasNextInt()) {
                        System.out.println("Invalid age! Please enter a valid number.");
                        scanner.next();
                    }
                    patient.setAge(scanner.nextInt());
                    break;
                case 4:
                    if (patient instanceof ChildPatient) {
                        System.out.print("Enter new Parent Name: ");
                        ((ChildPatient) patient).setParentName(scanner.nextLine());
                    } else if (patient instanceof TeenPatient) {
                        System.out.print("Enter new School Name: ");
                        ((TeenPatient) patient).setSchoolName(scanner.nextLine());
                    } else if (patient instanceof AdultPatient) {
                        System.out.print("Enter new Occupation: ");
                        ((AdultPatient) patient).setOccupation(scanner.nextLine());
                    }
                    break;
                case 5:
                    System.out.println("Going back to patient menu...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
            scanner.close();
        } while (choice != 5);
    }
    

    //generate the patientID
    private String generatePatientID() {
        int year = LocalDate.now().getYear();
        return year + String.format("%04d", patientCounter++);
    }
    

    public void samplePatients() {
        children.add(new ChildPatient(generatePatientID(), "Alice", "Smith", "09123456789", 5, "John Smith"));
        children.add(new ChildPatient(generatePatientID(), "Bob", "Johnson", "09234567890", 8, "Sarah Johnson"));
        
        teens.add(new TeenPatient(generatePatientID(), "Liam", "Miller", "09678901234", 16, "High School A"));
        teens.add(new TeenPatient(generatePatientID(), "Mia", "Taylor", "09789012345", 14, "High School B"));

        adults.add(new AdultPatient(generatePatientID(), "James", "Garcia", "09234567891", 30, "Engineer"));
        adults.add(new AdultPatient(generatePatientID(), "Sophia", "Martinez", "09345678902", 35, "Doctor"));
    }
    
}
