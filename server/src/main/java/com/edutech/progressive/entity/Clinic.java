package com.edutech.progressive.entity;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "clinic")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Clinic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "clinic_id")
    private Integer clinicId;

    @Column(name = "clinic_name", nullable = false)
    private String clinicName;

    @Column(name = "location")
    private String location;

    @Column(name = "contact_number")
    private String contactNumber;

    @Column(name = "established_year")
    private Integer establishedYear;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Doctor doctor;

    // ------------------------
    // Constructors
    // ------------------------
    public Clinic() {
    }

    public Clinic(Integer clinicId,
                  String clinicName,
                  String location,
                  String contactNumber,
                  Integer establishedYear,
                  Doctor doctor) {
        this.clinicId = clinicId;
        this.clinicName = clinicName;
        this.location = location;
        this.contactNumber = contactNumber;
        this.establishedYear = establishedYear;
        this.doctor = doctor;
    }

    // ------------------------
    // Getters / Setters
    // ------------------------
    public Integer getClinicId() {
        return clinicId;
    }

    public void setClinicId(Integer clinicId) {
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

    public Integer getEstablishedYear() {
        return establishedYear;
    }

    public void setEstablishedYear(Integer establishedYear) {
        this.establishedYear = establishedYear;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    // ------------------------
    // JDBC compatibility accessors for old DAO code
    // ------------------------
    @Transient
    public Integer getDoctorId() {
        return (doctor != null ? doctor.getDoctorId() : null);
    }

    public void setDoctorId(Integer doctorId) {
        if (doctorId == null) {
            this.doctor = null;
        } else {
            // Lightweight reference with only ID set
            Doctor d = new Doctor();
            d.setDoctorId(doctorId);
            this.doctor = d;
        }
    }

    // ------------------------
    // toString
    // ------------------------
    @Override
    public String toString() {
        return "Clinic{" +
                "clinicId=" + clinicId +
                ", clinicName='" + clinicName + '\'' +
                ", location='" + location + '\'' +
                ", contactNumber='" + contactNumber + '\'' +
                ", establishedYear=" + establishedYear +
                ", doctorId=" + getDoctorId() +
                '}';
    }
}