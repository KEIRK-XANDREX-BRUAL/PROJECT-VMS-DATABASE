package Main;

import vms.VaccineManagementSystem;
import java.sql.Connection;
import Database.data.VMSDatabase;
import vms.Menu;
import vms.Utilities;


public class Main {
    public static void main(String[] args) {
        VaccineManagementSystem vms = new VaccineManagementSystem();
        Utilities utilities = new Utilities();
        Menu menu = new Menu(vms, utilities);
        Connection connection = VMSDatabase.getConnection();
        vms.samplePatients();
        menu.displayMenu();
        if (connection != null) {
            System.out.println("Database connection established successfully.");
        } else {
            System.out.println("Failed to establish connection.");
        }
    }
}
