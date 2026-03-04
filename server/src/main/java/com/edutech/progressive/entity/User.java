package com.edutech.progressive.entity;
 
import javax.persistence.*;
 
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
 
@Entity
@Table(name = "user") 
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;
 
    @Column(name = "username", nullable = false, unique = true, length = 100)
    private String username;
 
    @Column(name = "password", nullable = false, length = 255)
    private String password;
 
    @Column(name = "role", nullable = false, length = 255)
    private String role;     
 
    @OneToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "patient_id", referencedColumnName = "patient_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Patient patient;
 
    @OneToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "doctor_id", referencedColumnName = "doctor_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Doctor doctor;  
 
    public User() {}
 
    public User(Integer userId, String username, String password, String role,
                Patient patient, Doctor doctor) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.role = role;
        this.patient = patient;
        this.doctor = doctor;
    }
 
    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }
 
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
 
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
 
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
 
    public Patient getPatient() { return patient; }
    public void setPatient(Patient patient) { this.patient = patient; }
 
    public Doctor getDoctor() { return doctor; }
    public void setDoctor(Doctor doctor) { this.doctor = doctor; }
}
 