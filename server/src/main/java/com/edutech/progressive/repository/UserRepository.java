package com.edutech.progressive.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.edutech.progressive.entity.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);

    @Query("SELECT u FROM User u WHERE u.doctor.doctorId = :doctorId")
    User findByDoctorId(@Param("doctorId") int doctorId);

    @Query("SELECT u FROM User u WHERE u.patient.patientId = :patientId")
    User findByPatientId(@Param("patientId") int patientId);

    @Modifying
    @Transactional
    @Query("DELETE FROM User u WHERE u.patient.patientId = :patientId")
    void deleteByPatientId(@Param("patientId") int patientId);

    @Modifying
    @Transactional
    @Query("DELETE FROM User u WHERE u.doctor.doctorId = :doctorId")
    void deleteByDoctorId(@Param("doctorId") int doctorId);
}
