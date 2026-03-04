package com.edutech.progressive.service.impl;
 
import com.edutech.progressive.entity.Billing;
import com.edutech.progressive.repository.BillingRepository;
import com.edutech.progressive.service.BillingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
 
import java.util.List;
 
@Primary
@Service
public class BillingServiceImpl implements BillingService {
 
    private static final String DEFAULT_STATUS = "Pending";
 
    private final BillingRepository billingRepository;
 
    @Autowired
    public BillingServiceImpl(BillingRepository billingRepository) {
        this.billingRepository = billingRepository;
    }
 
    @Override
    public List<Billing> getAllBills() {
        return billingRepository.findAll();
    }
 
    @Override
    public Integer createBill(Billing billing) {
 
        if (billing.getStatus() == null || billing.getStatus().trim().isEmpty()) {
            billing.setStatus(DEFAULT_STATUS);
        }
 
        if (billing.getAmount() == null) {
            billing.setAmount(0.0);
        }
 
 
        Billing saved = billingRepository.save(billing);
        return saved.getBillingId();
    }
 
    @Override
    public void updateBill(Billing billing) {
        if (billing.getBillingId() == null || billing.getBillingId() <= 0) {
            throw new IllegalArgumentException("Billing id is required for update");
        }
        Billing existing = billingRepository.findByBillingId(billing.getBillingId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Billing not found with id: " + billing.getBillingId()));
 
        if (billing.getStatus() != null) {
            existing.setStatus(billing.getStatus());
        }
 
        if (billing.getAppointment() != null) existing.setAppointment(billing.getAppointment());
        if (billing.getPatient() != null)     existing.setPatient(billing.getPatient());
        if (billing.getClinic() != null)      existing.setClinic(billing.getClinic());
        if (billing.getAmount() != null)      existing.setAmount(billing.getAmount());
        if (billing.getDateOfIssue() != null) existing.setDateOfIssue(billing.getDateOfIssue());
        if (billing.getDueDate() != null)     existing.setDueDate(billing.getDueDate());
        if (billing.getPaymentMethod() != null) existing.setPaymentMethod(billing.getPaymentMethod());
        if (billing.getNotes() != null)         existing.setNotes(billing.getNotes());
 
        billingRepository.save(existing);
    }
 
    @Override
    public Billing getBillById(int billingId) {
        return billingRepository.findByBillingId(billingId).orElse(null);
    }
 
    @Override
    public List<Billing> getBillsByPatientId(int patientId) {
        return billingRepository.findByPatient_PatientId(patientId);
    }
 
    @Override
    public List<Billing> getBillsByClinicId(int clinicId) {
        return billingRepository.findByClinic_ClinicId(clinicId);
    }
 
    @Override
    public List<Billing> getBillsByAppointmentId(int appointmentId) {
        return billingRepository.findByAppointment_AppointmentId(appointmentId);
    }
 
    @Override
    public List<Billing> getBillsByStatus(String status) {
        return billingRepository.findByStatus(status);
    }
 
    @Override
    public void deleteBill(int billingId) {
        try { billingRepository.deleteById(billingId); } catch (Exception ignored) {}
    }
}
 
 