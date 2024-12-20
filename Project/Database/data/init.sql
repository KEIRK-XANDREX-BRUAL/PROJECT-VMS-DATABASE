-- Create the database if it doesn't already exist
CREATE DATABASE IF NOT EXISTS VaccineManagementSystem;
USE VaccineManagementSystem;

-- Create Vaccine table
CREATE TABLE IF NOT EXISTS Vaccine (
    vaccine_id INT AUTO_INCREMENT PRIMARY KEY,
    vaccine_name VARCHAR(255) NOT NULL,
    dosage INT,
    doseInterval DATE
);

-- Create Patients table
CREATE TABLE IF NOT EXISTS Patients (
    patientID VARCHAR(20) PRIMARY KEY,
    firstName VARCHAR(50),
    lastName VARCHAR(50),
    contactNumber VARCHAR(15),
    age INT,
    additionalInfo TEXT
);

-- Create VaccineRecords table
CREATE TABLE IF NOT EXISTS VaccineRecords (
    recordID VARCHAR(20) PRIMARY KEY,
    patientID VARCHAR(20),
    vaccine_id INT,
    dateAdministered DATE,
    dosesAdministered INT,
    status ENUM('Completed', 'Not Completed'),
    FOREIGN KEY (patientID) REFERENCES Patients(patientID),
    FOREIGN KEY (vaccine_id) REFERENCES Vaccine(vaccine_id)
);

-- Show Tables
SHOW TABLES;

-- View patients table
SELECT * FROM patients;

-- View vaccines table
SELECT * FROM vaccines;

-- View vaccinerecords table
SELECT * FROM vaccinereords;
