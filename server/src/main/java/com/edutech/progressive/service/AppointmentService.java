package com.edutech.progressive.service;

import com.edutech.progressive.entity.Appointment;

import java.util.List;

public interface AppointmentService {

    List<Appointment> getAllAppointments();

    Integer createAppointment(Appointment appointment);

    void updateAppointment(Appointment appointment);

    Appointment getAppointmentById(int appointmentId);

    List<Appointment> getAppointmentsByClinicId(int clinicId);

    List<Appointment> getAppointmentsByPatientId(int patientId);

    List<Appointment> getAppointmentsByStatus(String status);
}