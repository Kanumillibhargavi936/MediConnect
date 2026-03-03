package com.edutech.progressive.dao;
 
import com.edutech.progressive.entity.Doctor;
 
import java.sql.SQLException;
import java.util.List;
 
public interface DoctorDAO {
    int addDoctor(Doctor doctor) throws SQLException;
    Doctor getDoctorById(int doctorId) throws SQLException;
    void updateDoctor(Doctor doctor) throws SQLException;
    void deleteDoctor(int doctorId) throws SQLException;
    List<Doctor> getAllDoctors() throws SQLException;
    List<Doctor> getDoctorSortedByExperience() throws SQLException;
}