package vms;

import java.util.InputMismatchException;
import java.util.Scanner;
import vaccine.Vaccines;
import patient.PatientDetails;

public class Menu {
    private VaccineManagementSystem vms;
    private Utilities utilities;

    public Menu(VaccineManagementSystem vms, Utilities utilities) {
        this.vms = vms;
        this.utilities = utilities;
    }

    public void displayMenu() {
        Scanner scanner = new Scanner(System.in);
        int choice = -1;

        do {
            try {
                utilities.designMenu();
                System.out.println("\t[1] Display Vaccines");
                System.out.println("\t[2] Add New Patient");
                System.out.println("\t[3] Display all Patients");
                System.out.println("\t[4] Edit Patient");
                System.out.println("\t[5] Exit");
                System.out.print("\tEnter your choice: ");

                
                while (!scanner.hasNextInt()) {
                    System.err.println("Invalid input. Please enter a numeric value for your choice.");
                    scanner.next(); 
                }
                choice = scanner.nextInt();
                scanner.nextLine(); 

                if (choice < 1 || choice > 5) {
                    System.out.println("Invalid choice. Please enter a number between 1 and 5.");
                    continue; 
                }

                switch (choice) {
                    case 1:
                        utilities.delay(500);
                        Vaccines.displayVaccineVault();
                        System.out.println("Press enter to continue...");
                        scanner.nextLine(); 
                        utilities.clearScreen();
                        break;
                    case 2:
                        utilities.delay(1000);
                        vms.addPatient();
                        System.out.println("Press enter to continue...");
                        scanner.nextLine();
                        utilities.clearScreen();
                        break;
                    case 3:

                        vms.displayAllPatients();
                        break;
                    case 4:
                        vms.displayAllPatients();
                        System.out.print("\nEnter the Patient ID to edit or view record: ");
                        String patientID = scanner.nextLine();
                        PatientDetails patient = vms.findPatientByID(patientID);

                        if (patient == null) {
                            System.out.println("Patient with ID " + patientID + " not found. Please try again.");
                        } else {
                            System.out.println("Selected Patient: " + patient.getFirstName() + " " + patient.getLastName());
                            utilities.delay(500);
                            vms.patientMenu(patient);
                        }
                        break;
                    case 5:
                        System.out.println("Exiting Vaccine Management System.");
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter a number between 1 and 5.");
                }
            } catch (InputMismatchException e) {
                System.err.println("Invalid input. Please enter a numeric value for your choice.");
                scanner.nextLine(); 
            }  catch (Exception e) {
                System.err.println("An unexpected error occurred: " + e.getMessage());
            }
        } while (choice != 5);
        scanner.close();
    }
}
