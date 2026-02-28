package com.edutech.progressive.service.impl;
 
import com.edutech.progressive.dao.DoctorDAO;
import com.edutech.progressive.entity.Doctor;
import com.edutech.progressive.service.DoctorService;
 
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
 
public class DoctorServiceImplJdbc implements DoctorService {
 
    private final DoctorDAO doctorDAO;
 
    public DoctorServiceImplJdbc(DoctorDAO doctorDAO) {
        this.doctorDAO = doctorDAO;
    }
 
    @Override
    public List<Doctor> getAllDoctors() throws Exception {
        try {
            return doctorDAO.getAllDoctors();
        } catch (SQLException e) {
            throw new Exception("Failed to fetch doctors", e);
        }
    }
 
    @Override
    public Integer addDoctor(Doctor doctor) throws Exception {
        try {
            return doctorDAO.addDoctor(doctor);
        } catch (SQLException e) {
            throw new Exception("Failed to add doctor", e);
        }
    }
 
    @Override
    public List<Doctor> getDoctorSortedByExperience() throws Exception {
        try {
            List<Doctor> all = doctorDAO.getAllDoctors();
            return all.stream()
                    .sorted(Comparator.comparingInt(d -> {
                        Integer y = d.getYearsOfExperience();
                        return y == null ? 0 : y;
                    }))
                    .collect(Collectors.toList());
        } catch (SQLException e) {
            throw new Exception("Failed to sort doctors by experience", e);
        }
    }
 
    @Override
    public void updateDoctor(Doctor doctor) throws Exception {
        try {
            doctorDAO.updateDoctor(doctor);
        } catch (SQLException e) {
            throw new Exception("Failed to update doctor", e);
        }
    }
 
    @Override
    public void deleteDoctor(int doctorId) throws Exception {
        try {
            doctorDAO.deleteDoctor(doctorId);
        } catch (SQLException e) {
            throw new Exception("Failed to delete doctor", e);
        }
    }
 
    @Override
    public Doctor getDoctorById(int doctorId) throws Exception {
        try {
            return doctorDAO.getDoctorById(doctorId);
        } catch (SQLException e) {
            throw new Exception("Failed to fetch doctor by id: " + doctorId, e);
        }
    }
}