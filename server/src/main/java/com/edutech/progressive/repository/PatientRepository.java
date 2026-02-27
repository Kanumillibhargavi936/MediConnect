package com.edutech.progressive.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.edutech.progressive.entity.Patient;
public interface PatientRepository extends JpaRepository<Patient, Integer> {
    Patient findByPatientId(int patientId);
}