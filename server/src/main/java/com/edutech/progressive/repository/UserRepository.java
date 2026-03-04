package com.edutech.progressive.repository;
 
import com.edutech.progressive.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
 
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
 
    User findByUsername(String username);
 
    void deleteByPatient_PatientId(int patientId);
 
    void deleteByDoctor_DoctorId(int doctorId);    
 
}