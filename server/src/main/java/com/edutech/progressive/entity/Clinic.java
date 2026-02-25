package com.edutech.progressive.entity;
 
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
 
@Entity
@Table(name = "clinic")
public class Clinic {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "clinic_id")
    private int clinicId;
 
    @Column(name = "clinic_name", nullable = false)
    private String clinicName;
 
    @Column(name = "location", length = 100)
    private String location;
 
    @Column(name = "doctor_id")
    private int doctorId;
 
    @Column(name = "contact_number", length = 15)
    private String contactNumber;
 
    @Column(name = "established_year")
    private int establishedYear;
 
    public Clinic() {
    }
 
    public Clinic(int clinicId, String clinicName, String location, int doctorId, String contactNumber, int establishedYear) {
        this.clinicId = clinicId;
        this.clinicName = clinicName;
        this.location = location;
        this.doctorId = doctorId;
        this.contactNumber = contactNumber;
        this.establishedYear = establishedYear;
    }
 
    public int getClinicId() { return clinicId; }
    public void setClinicId(int clinicId) { this.clinicId = clinicId; }
 
    public String getClinicName() { return clinicName; }
    public void setClinicName(String clinicName) { this.clinicName = clinicName; }
 
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
 
    public int getDoctorId() { return doctorId; }
    public void setDoctorId(int doctorId) { this.doctorId = doctorId; }
 
    public String getContactNumber() { return contactNumber; }
    public void setContactNumber(String contactNumber) { this.contactNumber = contactNumber; }
 
    public int getEstablishedYear() { return establishedYear; }
    public void setEstablishedYear(int establishedYear) { this.establishedYear = establishedYear; }
}