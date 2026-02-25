package com.edutech.progressive.service;
 
import com.edutech.progressive.entity.Doctor;
 
import java.util.List;
 
public interface DoctorService {
    List<Doctor> getAllDoctors() throws Exception;
    Integer addDoctor(Doctor doctor) throws Exception;
    void updateDoctor(Doctor doctor) throws Exception;
    void deleteDoctor(int doctorId) throws Exception;
    Doctor getDoctorById(int doctorId) throws Exception;
    List<Doctor> getDoctorSortedByExperience() throws Exception;
 
}