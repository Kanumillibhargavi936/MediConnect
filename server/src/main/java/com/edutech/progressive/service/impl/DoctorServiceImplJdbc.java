package com.edutech.progressive.service.impl;

import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import com.edutech.progressive.dao.DoctorDAO;
import com.edutech.progressive.entity.Doctor;
import com.edutech.progressive.service.DoctorService;

public class DoctorServiceImplJdbc implements DoctorService {

    private DoctorDAO doctorDAO;

    public DoctorServiceImplJdbc(DoctorDAO doctorDAO) {
        this.doctorDAO = doctorDAO;
    }

    @Override
    public List<Doctor> getAllDoctors() throws Exception {
        try {
            return doctorDAO.getAllDoctors();
        } catch (SQLException e) {
            throw new Exception("Error fetching all doctors", e);
        }
    }

    @Override
    public Doctor getDoctorById(int doctorId) throws Exception {
        try {
            Doctor doctor = doctorDAO.getDoctorById(doctorId);
            return doctor;
        } catch (Exception e) {
            throw new Exception("Error fetching doctor with ID " + doctorId, e);
        }
    }

    @Override
    public Integer addDoctor(Doctor doctor) throws Exception {
        try {
            return doctorDAO.addDoctor(doctor);
        } catch (SQLException e) {
            throw new Exception("Error adding doctor: " + doctor.getFullName(), e);
        }
    }

    @Override
    public List<Doctor> getDoctorSortedByExperience() throws Exception {
        try {
            List<Doctor> sortedDoctors = doctorDAO.getAllDoctors();
            if (!sortedDoctors.isEmpty()) {
                sortedDoctors.sort(Comparator.comparing(Doctor::getYearsOfExperience));
            }
            return sortedDoctors;
        } catch (SQLException e) {
            throw new Exception("Error fetching doctors sorted by experience ", e);
        }
    }

    @Override
    public void updateDoctor(Doctor doctor) throws Exception {
        try {
            doctorDAO.updateDoctor(doctor);
        } catch (SQLException e) {
            throw new Exception("Error updating doctor with ID " + doctor.getDoctorId(), e);
        }
    }

    @Override
    public void deleteDoctor(int doctorId) throws Exception {
        try {
            doctorDAO.deleteDoctor(doctorId);
        } catch (SQLException e) {
            throw new Exception("Error deleting doctor with ID " + doctorId, e);
        }
    }
}