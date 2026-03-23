package com.edutech.progressive.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.*;
import javax.persistence.Table;


@Entity
@Table(name = "patient")
public class Patient implements Comparable<Patient>{

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="patient_id")
    private int patientId;

    @Column(name = "full_name",nullable = false)
    private String fullName;

    @Column(name="date_of_birth")
    private Date dateOfBirth;

    @Column(name="contact_number")
    private String contactNumber;

    @Column(name = "email",nullable = false)
    private String email;

    @Column(name="address")
    private String address;


    public Patient() {
    }

    public Patient(int patientId, String fullName, Date dateOfBirth, String contactNumber, String email,
            String address) {
        this.patientId = patientId;
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
        this.contactNumber = contactNumber;
        this.email = email;
        this.address = address;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public int compareTo(Patient o) {
        // if(o==null || o.getFullName()==null ) return 1;
        // if(this.getFullName()==null) return -1;
        return this.fullName.compareToIgnoreCase(o.fullName);
    }

    

    

}