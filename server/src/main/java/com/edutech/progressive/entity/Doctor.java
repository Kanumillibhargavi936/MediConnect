package com.edutech.progressive.entity;
 
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
 
@Entity
@Table(name = "doctor")
public class Doctor {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "doctor_id")
    private int doctorId;
 
    @Column(name = "full_name", nullable = false)
    private String fullName;
 
    @Column(name = "specialty", length = 100)
    private String specialty;
 
    @Column(name = "contact_number", length = 15)
    private String contactNumber;
 
    @Column(name = "email", length = 100, nullable = false)
    private String email;
 
    @Column(name = "years_of_experience")
    private int yearsOfExperience;
 
    public Doctor() {
    }
 
    public Doctor(int doctorId, String fullName, String specialty, String contactNumber, String email, int yearsOfExperience) {
        this.doctorId = doctorId;
        this.fullName = fullName;
        this.specialty = specialty;
        this.contactNumber = contactNumber;
        this.email = email;
        this.yearsOfExperience = yearsOfExperience;
    }
 
    public int getDoctorId() { return doctorId; }
    public void setDoctorId(int doctorId) { this.doctorId = doctorId; }
 
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
 
    public String getSpecialty() { return specialty; }
    public void setSpecialty(String specialty) { this.specialty = specialty; }
 
    public String getContactNumber() { return contactNumber; }
    public void setContactNumber(String contactNumber) { this.contactNumber = contactNumber; }
 
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
 
    public int getYearsOfExperience() { return yearsOfExperience; }
    public void setYearsOfExperience(int yearsOfExperience) { this.yearsOfExperience = yearsOfExperience; }
}