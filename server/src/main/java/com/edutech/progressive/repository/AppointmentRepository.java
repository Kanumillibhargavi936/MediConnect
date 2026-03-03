package com.edutech.progressive.repository;

import com.edutech.progressive.entity.Appointment;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {

    Optional<Appointment> findByAppointmentId(int appointmentId);

    List<Appointment> findByClinic_ClinicId(int clinicId);

    List<Appointment> findByPatient_PatientId(int patientId);

    List<Appointment> findByStatus(String status);

    @Modifying
    @Transactional
    @Query("DELETE FROM Appointment a WHERE a.patient.patientId = :patientId")
    int deleteByPatientId(@Param("patientId") int patientId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Appointment a WHERE a.clinic.clinicId = :clinicId")
    int deleteByClinicId(@Param("clinicId") int clinicId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Appointment a WHERE a.clinic.doctor.doctorId = :doctorId")
    int deleteByDoctorId(@Param("doctorId") int doctorId);
}