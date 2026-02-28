package com.edutech.progressive.entity;
 
import javax.persistence.*;
 
@Entity
@Table(name = "clinic")
public class Clinic {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "clinic_id")
    private Integer clinicId;
 
    @Column(name = "clinic_name", nullable = false, length = 255)
    private String clinicName;
 
    @Column(name = "location", length = 100)
    private String location;
 
    @Column(name = "doctor_id")
    private Integer doctorId; // IMPORTANT for Day 7 (no relation yet)
 
    @Column(name = "contact_number", length = 15)
    private String contactNumber;
 
    @Column(name = "established_year")
    private Integer establishedYear;
 
    public Clinic() {}
 
    public Clinic(Integer clinicId, String clinicName, String location,
                  Integer doctorId, String contactNumber, Integer establishedYear) {
        this.clinicId = clinicId;
        this.clinicName = clinicName;
        this.location = location;
        this.doctorId = doctorId;
        this.contactNumber = contactNumber;
        this.establishedYear = establishedYear;
    }
 
    public Integer getClinicId() { return clinicId; }
    public void setClinicId(Integer clinicId) { this.clinicId = clinicId; }
 
    public String getClinicName() { return clinicName; }
    public void setClinicName(String clinicName) { this.clinicName = clinicName; }
 
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
 
    public Integer getDoctorId() { return doctorId; }
    public void setDoctorId(Integer doctorId) { this.doctorId = doctorId; }
 
    public String getContactNumber() { return contactNumber; }
    public void setContactNumber(String contactNumber) { this.contactNumber = contactNumber; }
 
    public Integer getEstablishedYear() { return establishedYear; }
    public void setEstablishedYear(Integer establishedYear) { this.establishedYear = establishedYear; }
}