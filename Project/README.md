# Immunization Tracker Project   🏥

## I. Project Overview   

This project is an Immunization Tracker developed using Java and SQL, designed to manage patient immunization records. The system allows users to store and retrieve vaccination data for different patient types (children, teens, adults). It aims to provide an intuitive interface to monitor vaccination statuses, ensuring that patients are up-to-date with required vaccinations based on age groups. The tracker also includes features for tracking the **next vaccination date** and the **remaining doses** of each vaccine.

**Features:** 💉💉
- Tracks vaccination records of children, teens, and adults.
- Allows users to select vaccines from a predefined list.
- Provides integration with SQL for persistent data storage.
- Calculates and records the next vaccination date based on schedules (e.g., 6 months, 1 year).
- Tracks remaining doses and marks patients as fully vaccinated when all doses are completed.

---

## II. Data Storage and SQL Integration 📂

The Immunization Tracker uses an SQL database to store and manage patient and vaccination data. This ensures data persistence and allows for easy retrieval and updates.

### SQL Schema Overview:

#### Tables:
1. **Patients**
   - `patient_id` (Primary Key)
   - `name`
   - `age`
   - `type` (Child, Teen, Adult)
   - `additional_info` (e.g., Parent's name for children, School name for teens, Occupation for adults)

2. **Vaccines**
   - `vaccine_id` (Primary Key)
   - `vaccine_name`
   - `schedule_interval` (e.g., 6 months, 1 year)
   - `total_doses`

3. **VaccinationRecords**
   - `record_id` (Primary Key)
   - `patient_id` (Foreign Key)
   - `next_vaccination_date`
   - `remaining_doses`
   - `status` (Completed, Not Completed)

### SQL Queries:

#### Insert a New Patient:
```sql
INSERT INTO Patients (name, age, type, additional_info) 
VALUES ('John Doe', 12, 'Teen', 'Springfield High School');
```

#### Record a Vaccination:
```sql
INSERT INTO VaccinationRecords (patient_id, vaccine_id, next_vaccination_date, remaining_doses) 
VALUES (1, 3, '2025-01-15', 2);
```

#### Update Remaining Doses:
```sql
UPDATE VaccinationRecords 
SET remaining_doses = remaining_doses - 1 
WHERE record_id = 1;
```

#### Retrieve Vaccination Status:
```sql
SELECT p.name, v.vaccine_name, vr.next_vaccination_date, vr.remaining_doses 
FROM Patients p 
JOIN VaccinationRecords vr ON p.patient_id = vr.patient_id 
JOIN Vaccines v ON vr.vaccine_id = v.vaccine_id;
```

---

## III. SDG Integration ⚕️ 🩺

This project supports **SDG 3: Good Health and Well-being**. By ensuring that individuals, especially children and teens, receive necessary vaccinations, the immunization tracker plays a crucial role in promoting health and preventing disease outbreaks. The tracker helps in timely vaccination, thereby contributing to a healthier and safer community.

---

## IV. Instructions for Running the Program

### 1. **Prerequisites**
   Before running the program, ensure you have the necessary tools installed:
   - **Java JDK 8 or later**: Download and install it from the [official Java website](https://www.oracle.com/java/technologies/javase-jdk8-downloads.html).
   - **SQL Database**: Install a database server like MySQL or PostgreSQL.
   - **A Code Editor or IDE**: Use any text editor (e.g., Visual Studio Code, Sublime Text) or Java IDE (e.g., IntelliJ IDEA, Eclipse).

### 2. **Download and Extract the Project**
   - Download the project ZIP file from the repository or your source.
   - Extract the ZIP file to a folder on your computer.

### 3. **Set Up the Database**
   - Import the provided SQL schema file to your database server.
   - Update the database configuration in the Java project files to connect to your database.

### 4. **Navigate to the Project Folder**
   - Open a terminal (or Command Prompt) and navigate to the folder where you extracted the ZIP file:
     ```bash
     cd path/to/extracted/folder
     ```

### 5. **Run the Application**
   - Compile and run the application using your preferred IDE or the terminal.