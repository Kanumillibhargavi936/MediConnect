package com.edutech.progressive.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.edutech.progressive.entity.Doctor;
public interface DoctorRepository extends JpaRepository<Doctor, Integer> {
}