package com.edutech.progressive.controller;
 
import com.edutech.progressive.entity.Appointment;
import com.edutech.progressive.service.AppointmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
 
import java.util.List;
 
@RestController
@RequestMapping("/appointment")
public class AppointmentController {
 
    private final AppointmentService appointmentService;
 
    public AppointmentController(AppointmentService appointmentService){
        this.appointmentService = appointmentService;
    }
 
    @GetMapping
    public ResponseEntity<?> getAllAppointments() {
        try {
            List<Appointment> list = appointmentService.getAllAppointments();
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching appointments: " + e.getMessage());
        }
    }
 
    @PostMapping
    public ResponseEntity<?> createAppointment(@RequestBody Appointment appointment) {
        try {
            Integer id = appointmentService.createAppointment(appointment);
            Appointment saved = appointmentService.getAppointmentById(id);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error creating appointment: " + e.getMessage());
        }
    }
 
    @PutMapping("/{appointmentId}")
    public ResponseEntity<?> updateAppointment(@PathVariable int appointmentId,
                                               @RequestBody Appointment appointment) {
        try {
            appointment.setAppointmentId(appointmentId);
            appointmentService.updateAppointment(appointment);
            Appointment updated = appointmentService.getAppointmentById(appointmentId);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating appointment: " + e.getMessage());
        }
    }
 
    @GetMapping("/{appointmentId}")
    public ResponseEntity<?> getAppointmentById(@PathVariable int appointmentId) {
        try {
            Appointment appt = appointmentService.getAppointmentById(appointmentId);
            return ResponseEntity.ok(appt);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching appointment: " + e.getMessage());
        }
    }
 
    @GetMapping("/clinic/{clinicId}")
    public ResponseEntity<?> getAppointmentsByClinicId(@PathVariable int clinicId) {
        try {
            return ResponseEntity.ok(appointmentService.getAppointmentsByClinicId(clinicId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching appointments by clinic: " + e.getMessage());
        }
    }
 
    @GetMapping("/patient/{patientId}")
    public ResponseEntity<?> getAppointmentsByPatientId(@PathVariable int patientId) {
        try {
            return ResponseEntity.ok(appointmentService.getAppointmentsByPatientId(patientId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching appointments by patient: " + e.getMessage());
        }
    }
 
    @GetMapping("/status/{status}")
    public ResponseEntity<?> getAppointmentsByStatus(@PathVariable String status) {
        try {
            return ResponseEntity.ok(appointmentService.getAppointmentsByStatus(status));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching appointments by status: " + e.getMessage());
        }
    }
}