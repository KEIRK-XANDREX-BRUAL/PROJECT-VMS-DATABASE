package vms;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Database.data.PatientDAO;
import Database.data.VaccineRecordDAO;

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
    private PatientDAO patientDAO;
    private VaccineRecordDAO vaccineRecordDAO;

    public VaccineManagementSystem() {
        this.children = new ArrayList<>();
        this.teens = new ArrayList<>();
        this.adults = new ArrayList<>();
        this.patientCounter = 1;
        this.scanner = new Scanner(System.in);
        this.patientDAO = new PatientDAO();
        this.vaccineRecordDAO = new VaccineRecordDAO();
        Vaccines.vaccineVault();
        loadPatientsFromDatabase();
    }

    // Add a new patient
    public void addPatient() {
        try {
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
                PatientDetails patient = null;
                String additionalInfo = null;

                if (age <= 12) {
                    System.out.print("Enter Parent Name: ");
                    additionalInfo = scanner.nextLine();
                    patient = new ChildPatient(patientID, firstName, lastName, contactNumber, age, additionalInfo);
                } else if (age > 12 && age < 21) {
                    System.out.print("Enter School Name: ");
                    additionalInfo = scanner.nextLine();
                    patient = new TeenPatient(patientID, firstName, lastName, contactNumber, age, additionalInfo);
                } else {
                    System.out.print("Enter Occupation: ");
                    additionalInfo = scanner.nextLine();
                    patient = new AdultPatient(patientID, firstName, lastName, contactNumber, age, additionalInfo);
                }

                patientDAO.addPatient(patient, additionalInfo);
                System.out.println("Patient added successfully.");
            }   catch (SQLException e) {
                System.err.println("Error adding patient: " + e.getMessage());
        }
    }

    // Administer Vaccine
    public void administerVaccine(PatientDetails patient) {
        if (patient == null) return;
    
        try {
            System.out.println("Selected Patient: " + patient.getFirstName() + " " + patient.getLastName());
            Vaccines.displayVaccineVault();
    
            System.out.print("Enter Vaccine ID to administer: ");
            String vaccineId = scanner.nextLine().trim();
    
            List<Vaccines> vaccineVault = Vaccines.getVaccineVault();
            Vaccines selectedVaccine = vaccineVault.stream()
                    .filter(vaccine -> vaccine.getVaccineId().equalsIgnoreCase(vaccineId))
                    .findFirst()
                    .orElse(null);
    
            if (selectedVaccine == null) {
                System.out.println("Invalid Vaccine ID.");
                return;
            }
    
            VaccineRecord record = patient.getVaccineRecord(selectedVaccine);
    
            if (record == null) {
                record = new VaccineRecord(selectedVaccine);
            }
    
            if (record.getDosesAdministered() >= selectedVaccine.getRequiredDoses()) {
                System.out.println("All doses for this vaccine are already completed.");
            } else {
                record.administerDose();
                patient.addVaccineRecord(record);
                vaccineRecordDAO.addVaccineRecord(record, patient.getPatientID());
    
                System.out.println("Vaccine administered successfully.");
                if (record.getNextVaccinationDate() != null) {
                    System.out.println("Next vaccination date: " + record.getNextVaccinationDate());
                }
            }
        } catch (SQLException e) {
            System.err.println("Database error while administering vaccine: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
        }
    }
    

    public void loadPatientsFromDatabase() {
        try {
            List<PatientDetails> allPatients = patientDAO.getAllPatients();
            for (PatientDetails patient : allPatients) {
                if (patient.getAge() <= 12) {
                    children.add(patient);
                } else if (patient.getAge() > 12 && patient.getAge() < 21) {
                    teens.add(patient);
                } else {
                    adults.add(patient);
                }
            }
            System.out.println("Patients loaded from the database.");
        } catch (SQLException e) {
            System.out.println("Error: Unable to load patients from the database.");
        }
    }
    

    //display patient's records 
    public void patientRecords(PatientDetails patient) {
        if (patient == null) return;

        try {
            patient.displayPatientInfo();
            List<VaccineRecord> records = vaccineRecordDAO.getVaccineRecordsByPatientId(patient.getPatientID());

            System.out.println("\nVaccine Records:");
            for (VaccineRecord record : records) {
                System.out.println("Vaccine: " + record.getVaccine().getVaccineName() +
                        ", Doses Administered: " + record.getDosesAdministered() +
                        ", Status: " + record.getStatus());
            }
        } catch (SQLException e) {
            System.err.println("Error fetching vaccine records: " + e.getMessage());
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

    // find patient by ID
    public PatientDetails findPatientByID(String patientID) {
        try {
            return patientDAO.getPatientById(patientID);
        } catch (SQLException e) {
            System.err.println("Error finding patient: " + e.getMessage());
            return null;
        }
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
        return "P" + String.format("%04d", patientCounter++);
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
