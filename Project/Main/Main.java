package Main;

import vms.VaccineManagementSystem;
import vms.Menu;
import vms.Utilities;
 
public class Main {
    public static void main(String[] args) {
        VaccineManagementSystem vms = new VaccineManagementSystem();
        Utilities utilities = new Utilities();

        Menu menu = new Menu(vms, utilities);
        vms.samplePatients();
        menu.displayMenu();
        //implement concise user handling try catch methods
        //implement database
    }
}
