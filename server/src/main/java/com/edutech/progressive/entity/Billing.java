package com.edutech.progressive.entity;

import javax.persistence.*;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "billing")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Billing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "billing_id")
    private Integer billingId;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "appointment_id", nullable = true)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Appointment appointment;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "patient_id", nullable = true)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Patient patient;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "clinic_id", nullable = true)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Clinic clinic;

    @Column(name = "amount", nullable = false)
    private Double amount;

    @Column(name = "status", nullable = false)
    private String status;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_of_issue", nullable = true)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "UTC")
    private Date dateOfIssue;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "due_date", nullable = true)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "UTC")
    private Date dueDate;

    @Column(name = "payment_method")
    private String paymentMethod;

    @Column(name = "notes")
    private String notes;

    public Billing() {}

    public Billing(Integer billingId,
                   Appointment appointment,
                   Patient patient,
                   Clinic clinic,
                   Double amount,
                   String status,
                   Date dateOfIssue,
                   Date dueDate,
                   String paymentMethod,
                   String notes) {
        this.billingId = billingId;
        this.appointment = appointment;
        this.patient = patient;
        this.clinic = clinic;
        this.amount = amount;
        this.status = status;
        this.dateOfIssue = dateOfIssue;
        this.dueDate = dueDate;
        this.paymentMethod = paymentMethod;
        this.notes = notes;
    }

    public Integer getBillingId() { return billingId; }
    public void setBillingId(Integer billingId) { this.billingId = billingId; }

    public Appointment getAppointment() { return appointment; }
    public void setAppointment(Appointment appointment) { this.appointment = appointment; }

    public Patient getPatient() { return patient; }
    public void setPatient(Patient patient) { this.patient = patient; }

    public Clinic getClinic() { return clinic; }
    public void setClinic(Clinic clinic) { this.clinic = clinic; }

    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Date getDateOfIssue() { return dateOfIssue; }
    public void setDateOfIssue(Date dateOfIssue) { this.dateOfIssue = dateOfIssue; }

    public Date getDueDate() { return dueDate; }
    public void setDueDate(Date dueDate) { this.dueDate = dueDate; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}