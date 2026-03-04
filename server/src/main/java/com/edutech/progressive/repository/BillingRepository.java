 
package com.edutech.progressive.repository;
 
import com.edutech.progressive.entity.Billing;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
 
import java.util.List;
import java.util.Optional;
 
@Repository
public interface BillingRepository extends JpaRepository<Billing, Integer> {
 
    Optional<Billing> findByBillingId(int billingId);
 
    List<Billing> findByPatient_PatientId(int patientId);
 
    List<Billing> findByClinic_ClinicId(int clinicId);
 
    List<Billing> findByAppointment_AppointmentId(int appointmentId);
 
    List<Billing> findByStatus(String status);
 
    @Modifying
    @Transactional
    @Query("DELETE FROM Billing b WHERE b.patient.patientId = :patientId")
    int deleteByPatientId(@Param("patientId") int patientId);
 
    @Modifying
    @Transactional
    @Query("DELETE FROM Billing b WHERE b.clinic.clinicId = :clinicId")
    int deleteByClinicId(@Param("clinicId") int clinicId);
 
    @Modifying
    @Transactional
    @Query("DELETE FROM Billing b WHERE b.appointment.appointmentId = :appointmentId")
    int deleteByAppointmentId(@Param("appointmentId") int appointmentId);
 
    @Modifying
    @Transactional
    @Query("DELETE FROM Billing b WHERE b.clinic.doctor.doctorId = :doctorId")
    int deleteByDoctorId(@Param("doctorId") int doctorId);
}
 