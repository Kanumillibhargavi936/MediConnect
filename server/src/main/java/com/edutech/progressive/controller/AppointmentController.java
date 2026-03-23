package com.edutech.progressive.controller;

import com.edutech.progressive.entity.Appointment;
import org.springframework.http.ResponseEntity;

import java.util.List;

import com.edutech.progressive.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @GetMapping
    public ResponseEntity<List<Appointment>> getAllAppointments() {
        List<Appointment> appointments = appointmentService.getAllAppointments();
        return new ResponseEntity<>(appointments, HttpStatus.OK);
    }

    @GetMapping("/{appointmentId}")
    public ResponseEntity<Appointment> getAppointmentById(@PathVariable Integer appointmentId) {
        Appointment appointment = appointmentService.getAppointmentById(appointmentId);
        return new ResponseEntity<>(appointment, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Integer> createAppointment(@RequestBody Appointment appointment) {
        Integer appointmentId = appointmentService.createAppointment(appointment);
        return new ResponseEntity<>(appointmentId, HttpStatus.CREATED);
    }

    @PutMapping("/{appointmentId}")
    public ResponseEntity<Void> updateAppointment(@PathVariable Integer appointmentId, @RequestBody Appointment appointment) {
        appointment.setAppointmentId(appointmentId);
        appointmentService.updateAppointment(appointment);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/clinic/{clinicId}")
    public ResponseEntity<List<Appointment>> getAppointmentsByClinic(@PathVariable Integer clinicId) {
        List<Appointment> appointments = appointmentService.getAppointmentByClinic(clinicId);
        return new ResponseEntity<>(appointments, HttpStatus.OK);
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<Appointment>> getAppointmentsByPatient(@PathVariable Integer patientId) {
        List<Appointment> appointments = appointmentService.getAppointmentByPatient(patientId);
        return new ResponseEntity<>(appointments, HttpStatus.OK);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Appointment>> getAppointmentsByStatus(@PathVariable String status) {
        List<Appointment> appointments = appointmentService.getAppointmentByStatus(status);
        return new ResponseEntity<>(appointments, HttpStatus.OK);
    }
}

