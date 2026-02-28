 
package com.edutech.progressive.entity;
 
import javax.persistence.*;
 
@Entity
@Table(name = "doctor")
public class Doctor {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "doctor_id")
    private Integer doctorId;
 
    @Column(name = "full_name", nullable = false, length = 255)
    private String fullName;
 
    @Column(name = "specialty", length = 100)
    private String specialty;
 
    @Column(name = "contact_number", length = 15)
    private String contactNumber;
 
    @Column(name = "email", nullable = false, length = 100)
    private String email;
 
    @Column(name = "years_of_experience")
    private Integer yearsOfExperience;
 
    public Doctor() {}
 
    public Doctor(Integer doctorId, String fullName, String specialty,
                  String contactNumber, String email, Integer yearsOfExperience) {
        this.doctorId = doctorId;
        this.fullName = fullName;
        this.specialty = specialty;
        this.contactNumber = contactNumber;
        this.email = email;
        this.yearsOfExperience = yearsOfExperience;
    }
 
    public Integer getDoctorId() { return doctorId; }
    public void setDoctorId(Integer doctorId) { this.doctorId = doctorId; }
 
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
 
    public String getSpecialty() { return specialty; }
    public void setSpecialty(String specialty) { this.specialty = specialty; }
 
    public String getContactNumber() { return contactNumber; }
    public void setContactNumber(String contactNumber) { this.contactNumber = contactNumber; }
 
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
 
    public Integer getYearsOfExperience() { return yearsOfExperience; }
    public void setYearsOfExperience(Integer yearsOfExperience) { this.yearsOfExperience = yearsOfExperience; }
}