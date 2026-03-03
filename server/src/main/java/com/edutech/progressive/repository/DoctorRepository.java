package com.edutech.progressive.repository;

import com.edutech.progressive.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Integer> {

    Optional<Doctor> findByDoctorId(int doctorId);

    // Unique email lookup (needed by seeding/guards)
    Optional<Doctor> findByEmail(String email);

    // Fast existence check for idempotent seeding/guards
    boolean existsByEmail(String email);
}