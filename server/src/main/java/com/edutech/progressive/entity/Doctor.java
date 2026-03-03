package com.edutech.progressive.entity;
 
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
 
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
 
@Entity
@Table(name = "doctor")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Doctor {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "doctor_id")
    private Integer doctorId;
 
    @Column(name = "full_name", nullable = false)
    private String fullName;
 
    @Column(name = "specialty")
    private String specialty;
 
    @Column(name = "contact_number")
    private String contactNumber;
 
    @Column(name = "email", nullable = false)
    private String email;
 
    @Column(name = "years_of_experience")
    private Integer yearsOfExperience;
 
    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore // prevent infinite recursion: doctor -> clinics -> doctor -> ...
    private List<Clinic> clinics = new ArrayList<>();
 
    public Doctor() {}
 
    // getters and setters
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
 
    public List<Clinic> getClinics() { return clinics; }
    public void setClinics(List<Clinic> clinics) { this.clinics = clinics; }
}