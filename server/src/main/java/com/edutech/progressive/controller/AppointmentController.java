package com.edutech.progressive.controller;

import com.edutech.progressive.entity.Appointment;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class AppointmentController {

    public ResponseEntity<List<Appointment>> getAllAppointments() {
        return null;
    }

    public ResponseEntity<Integer> createAppointment(Appointment appointment) {
        return null;
    }

    public ResponseEntity<Void> updateAppointment(int appointmentId, Appointment appointment) {
        return null;
    }

    public ResponseEntity<Appointment> getAppointmentById(int appointmentId) {
        return null;
    }

    public ResponseEntity<List<Appointment>> getAppointmentByClinic(int clinicId) {
        return null;
    }

    public ResponseEntity<List<Appointment>> getAppointmentByPatient(int patientId) {
        return null;
    }

    public ResponseEntity<List<Appointment>> getAppointmentByStatus(String status) {
        return null;
    }
}
