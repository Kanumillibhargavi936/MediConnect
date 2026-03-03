package com.edutech.progressive.repository;

import com.edutech.progressive.entity.Clinic;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClinicRepository extends JpaRepository<Clinic, Integer> {

    Optional<Clinic> findByClinicId(int clinicId);

    List<Clinic> findAllByLocation(String location);

    Optional<Clinic> findByClinicName(String clinicName);

    @Query("SELECT c FROM Clinic c WHERE c.doctor.doctorId = :doctorId")
    List<Clinic> findAllByDoctorId(@Param("doctorId") int doctorId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Clinic c WHERE c.doctor.doctorId = :doctorId")
    int deleteByDoctorId(@Param("doctorId") int doctorId);
}