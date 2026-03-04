 
package com.edutech.progressive.service;
 
import com.edutech.progressive.entity.Billing;
import java.util.List;
 
public interface BillingService {
 
    List<Billing> getAllBills();
 
    Integer createBill(Billing billing);
 
    void updateBill(Billing billing);
 
    Billing getBillById(int billingId);
 
    List<Billing> getBillsByPatientId(int patientId);
 
    List<Billing> getBillsByClinicId(int clinicId);
 
    List<Billing> getBillsByAppointmentId(int appointmentId);
 
    List<Billing> getBillsByStatus(String status);
 
    void deleteBill(int billingId);
}
 