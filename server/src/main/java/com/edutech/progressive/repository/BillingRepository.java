package com.edutech.progressive.repository;


import com.edutech.progressive.entity.Billing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface BillingRepository extends JpaRepository<Billing, Integer> {

    List<Billing> findAllByPatient_PatientId(int patientId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Billing b WHERE b.patient.patientId = :patientId")
    void deleteByPatientId(@Param("patientId") int patientId);
}