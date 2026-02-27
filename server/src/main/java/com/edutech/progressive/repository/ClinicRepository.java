package com.edutech.progressive.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.edutech.progressive.entity.Clinic;
public interface ClinicRepository extends JpaRepository<Clinic, Integer> {
}