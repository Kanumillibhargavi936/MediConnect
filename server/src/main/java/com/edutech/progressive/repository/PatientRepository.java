package com.edutech.progressive.repository;
 
import com.edutech.progressive.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
 
import java.util.Optional;
 
@Repository
public interface PatientRepository extends JpaRepository<Patient, Integer> {
 
    Optional<Patient> findByPatientId(int patientId);
 
    // ✅ Day 9: find by email for uniqueness check and lookups
    Optional<Patient> findByEmail(String email);
}