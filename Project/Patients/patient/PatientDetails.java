package patient;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import vaccine.VaccineRecord;
import vaccine.Vaccines;
import Database.data.VaccineRecordDAO;


public abstract class PatientDetails {
    protected String patientID;
    protected String firstName;
    protected String lastName;
    protected String contactNumber;
    protected int age;
    protected Scanner scanner;

    private List<VaccineRecord> vaccineRecords;
    private VaccineRecordDAO vaccineRecordDAO;

    public PatientDetails(String patientID, String firstName, String lastName,String contactNumber, int age){
        this.patientID = patientID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.contactNumber = contactNumber;
        this.age = age;
        this.vaccineRecords = new ArrayList<>();
        this.vaccineRecordDAO = new VaccineRecordDAO();
    }
    
    public String getPatientID(){
        return patientID;
    }

    public String getFirstName(){
        return firstName;
    }

    public String getLastName(){
        return lastName;
    }

    public String getContactNumber(){
        return contactNumber;
    }
    
    public int getAge(){
        return age;
    }

    public Scanner getScanner(){
        return this.scanner;
    }

    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

    public void setLastName(String lastName){
        this.lastName = lastName;
    }

    public void setContactNumber(String contactNumber){
        this.contactNumber = contactNumber;
    }

    public void setAge(int age){
        this.age = age;
    }

    public void addVaccineRecord(VaccineRecord record) {
        for (int i = 0; i < vaccineRecords.size(); i++) {
            if (vaccineRecords.get(i).getVaccine().equals(record.getVaccine())) {
                vaccineRecords.set(i, record);
                return;
            }
        }
        vaccineRecords.add(record);
    }

    

    public void loadVaccineRecords() {
        try {
            this.vaccineRecords = vaccineRecordDAO.getVaccineRecordsByPatientId(patientID);
        }catch (Exception e) {
            System.err.println("Error fetching vaccine records: " + e.getMessage());
        }
    }
    

    public List<VaccineRecord> getVaccineRecords(){
        return vaccineRecords;
    }

    public VaccineRecord getVaccineRecord(Vaccines vaccine) {
        for (int i = 0; i < vaccineRecords.size(); i++) {
            VaccineRecord record = vaccineRecords.get(i);
            if (record.getVaccine().equals(vaccine)) {
                return record; 
            }
        }
        return null;
    }


    public abstract void displayPatientInfo();

    public abstract void displayPatientRecords();
}
