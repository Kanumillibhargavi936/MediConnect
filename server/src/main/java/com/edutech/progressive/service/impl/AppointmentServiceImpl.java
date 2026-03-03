package com.edutech.progressive.service.impl;
 
import com.edutech.progressive.entity.Appointment;
import com.edutech.progressive.repository.AppointmentRepository;
import com.edutech.progressive.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
 
import java.util.Arrays;
import java.util.List;
 
@Primary
@Service
public class AppointmentServiceImpl implements AppointmentService {
 
    private static final List<String> ALLOWED_STATUS =
            Arrays.asList("Scheduled", "Completed", "Canceled");
 
    private final AppointmentRepository appointmentRepository;
 
    @Autowired
    public AppointmentServiceImpl(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }
 
    @Override
    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }
 
    @Override
    public Integer createAppointment(Appointment appointment) {
        validateStatusOrThrow(appointment.getStatus());
        Appointment saved = appointmentRepository.save(appointment);
        return saved.getAppointmentId();
    }
 
    @Override
    public void updateAppointment(Appointment appointment) {
        if (appointment.getAppointmentId() == null || appointment.getAppointmentId() <= 0) {
            throw new IllegalArgumentException("Appointment id is required for update");
        }
 
        if (appointment.getStatus() != null) {
            validateStatusOrThrow(appointment.getStatus());
        }
 
        Appointment existing = appointmentRepository.findByAppointmentId(appointment.getAppointmentId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Appointment not found with id: " + appointment.getAppointmentId()));
 
        if (appointment.getPatient() != null) {
            existing.setPatient(appointment.getPatient());
        }
        if (appointment.getClinic() != null) {
            existing.setClinic(appointment.getClinic());
        }
        if (appointment.getAppointmentDate() != null) {
            existing.setAppointmentDate(appointment.getAppointmentDate());
        }
        if (appointment.getStatus() != null) {
            existing.setStatus(appointment.getStatus());
        }
        if (appointment.getPurpose() != null) {
            existing.setPurpose(appointment.getPurpose());
        }
 
        appointmentRepository.save(existing);
    }
 
    @Override
    public Appointment getAppointmentById(int appointmentId) {
        return appointmentRepository.findByAppointmentId(appointmentId).orElse(null);
    }
 
    @Override
    public List<Appointment> getAppointmentsByClinicId(int clinicId) {
        return appointmentRepository.findByClinic_ClinicId(clinicId);
    }
 
    @Override
    public List<Appointment> getAppointmentsByPatientId(int patientId) {
        return appointmentRepository.findByPatient_PatientId(patientId);
    }
 
    @Override
    public List<Appointment> getAppointmentsByStatus(String status) {
        return appointmentRepository.findByStatus(status);
    }
 
    private static void validateStatusOrThrow(String status) {
        if (status == null || !ALLOWED_STATUS.contains(status)) {
            throw new IllegalArgumentException(
                    "Invalid status. Allowed: Scheduled, Completed, Canceled");
        }
    }
}