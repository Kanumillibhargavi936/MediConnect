package com.edutech.progressive.repository;
 
import com.edutech.progressive.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
 
import java.util.Optional;
 
@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Integer> {
    Optional<Doctor> findByDoctorId(int doctorId);
 
    // ✅ Day 9: unique email check
    Optional<Doctor> findByEmail(String email);
}