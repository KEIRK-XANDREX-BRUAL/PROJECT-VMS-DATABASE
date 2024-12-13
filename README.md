# Immunization Tracker Project   🏥 

## I. Project Overview   

This project is an Immunization Tracker developed using Java, which is designed to manage patient immunization records. The system allows users to store and retrieve vaccination data for different patient types (children, teens, adults). The project leverages object-oriented programming principles such as inheritance, polymorphism, encapsulation, and abstraction. 

The tracker aims to provide an intuitive interface to monitor vaccination statuses, ensuring that patients are up-to-date with required vaccinations based on age groups. It also includes features for tracking the **next vaccination date** and the **remaining doses** of each vaccine.

**Features:** 💉💉
- Tracks vaccination records of children, teens, and adults.
- Allows users to select vaccines from a predefined list.
- Utilizes OOP principles to structure the application logically.

- **Next Vaccination Date**: The system automatically calculates and records the next vaccination date after each vaccine administration, based on the vaccine's schedule (e.g., 6 months, 1 year).
  
- **Remaining Doses**: After each vaccine dose is administered, the system tracks the remaining doses. Once all doses are taken, the patient is marked as fully vaccinated.

---

---

## II. **Explanation of How OOP Principles Were Applied** 📋

### 1. **Inheritance**
   - **Patient Class**: The base class for all patient types.
   - **Child, Teen, Adult Classes**: Derived from `Patient` and each has specific attributes:
     - **Child**: Parent's name.
     - **Teen**: School name.
     - **Adult**: Occupation.

### 2. **Polymorphism**
   - Methods in the `Patient` class are overridden in child classes to customize behavior, such as displaying patient details differently for children, teens, and adults.

### 3. **Encapsulation**
   - Patient data (e.g., name, age, vaccine records) is encapsulated within classes, accessed only via getter and setter methods, ensuring data integrity.

### 4. **Abstraction**
   - The `VaccineRecord` class abstracts the vaccine management logic. Users interact with simple methods for vaccine administration without dealing with complex internal details.

---

## III. SDG Integration ⚕️ 🩺

This project supports **SDG 3: Good Health and Well-being**. By ensuring that individuals, especially children and teens, receive necessary vaccinations, the immunization tracker plays a crucial role in promoting health and preventing disease outbreaks. The tracker helps in timely vaccination, thereby contributing to a healthier and safer community.

---

## IV. Instructions for Running the Program

### 1. **Prerequisites**
   Before running the program, ensure you have the necessary tools installed:
   - **Java JDK 8 or later**: Download and install it from the [official Java website](https://www.oracle.com/java/technologies/javase-jdk8-downloads.html).
   - **A Code Editor or IDE**: Use any text editor (e.g., Visual Studio Code, Sublime Text) or Java IDE (e.g., IntelliJ IDEA, Eclipse).

### 2. **Download and Extract the Project**
   - Download the project ZIP file from the repository or your source.
   - Extract the ZIP file to a folder on your computer.

### 3. **Navigate to the Project Folder**
   - Open a terminal (or Command Prompt) and navigate to the folder where you extracted the ZIP file.
   ```bash
   cd path/to/extracted/folder
