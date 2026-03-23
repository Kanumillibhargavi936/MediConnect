package com.edutech.progressive.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name="clinic")
public class Clinic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="clinic_id")
    private int clinicId;

    @Column(name = "clinic_name",nullable = false)
    private String clinicName;

    @Column(name="location")
    private String location;

    @Column(name="contact_number")
    private String contactNumber;

    @Column(name="established_year")
    private int establishedYear;


    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    public Clinic() {
    }

    

    public Clinic(int clinicId, String clinicName, String location, String contactNumber, int establishedYear,Doctor doctor) {
        this.clinicId = clinicId;
        this.clinicName = clinicName;
        this.location = location;
        this.contactNumber = contactNumber;
        this.establishedYear = establishedYear;
        this.doctor = doctor;
    }



    public int getClinicId() {
        return clinicId;
    }

    public void setClinicId(int clinicId) {
        this.clinicId = clinicId;
    }

    public String getClinicName() {
        return clinicName;
    }

    public void setClinicName(String clinicName) {
        this.clinicName = clinicName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    
    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public int getEstablishedYear() {
        return establishedYear;
    }

    public void setEstablishedYear(int establishedYear) {
        this.establishedYear = establishedYear;
    }



    public Doctor getDoctor() {
        return doctor;
    }



    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    

    


    


}