package com.edutech.progressive.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.edutech.progressive.entity.Appointment;

@Repository
public interface AppointmentRepository  extends JpaRepository<Appointment,Integer>{

    List<Appointment> findByClinic_ClinicId(int clinicId);
    List<Appointment> findByPatient_PatientId(int patientId);
    List<Appointment> findByStatus(String status);

    @Modifying
    @Transactional
    @Query("DELETE FROM Appointment a WHERE a.clinic.doctor.doctorId = :doctorId")
    void deleteByDoctorId(@Param("doctorId") int doctorId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Appointment a WHERE a.patient.patientId = :patientId")
    void deleteByPatientId(@Param("patientId") int patientId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Appointment a WHERE a.clinic.clinicId = :clinicId")
    void deleteByClinicId(@Param("clinicId") int clinicId);
}
