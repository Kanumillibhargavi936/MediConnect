package com.edutech.progressive.service.impl;
import com.edutech.progressive.dao.ClinicDAO;
import com.edutech.progressive.entity.Clinic;
import com.edutech.progressive.service.ClinicService;
import java.sql.SQLException;
import java.util.List;
public class ClinicServiceImplJdbc implements ClinicService {
    private final ClinicDAO clinicDAO;
    public ClinicServiceImplJdbc(ClinicDAO clinicDAO) {
        this.clinicDAO = clinicDAO;
    }
    @Override
    public List<Clinic> getAllClinics() {
        try {
            return clinicDAO.getAllClinics();
        } catch (SQLException e) {
            // Wrap to unchecked to keep the interface signature clean
            throw new RuntimeException("Failed to fetch clinics", e);
        }
    }
    @Override
    public Clinic getClinicById(int clinicId) {
        try {
            return clinicDAO.getClinicById(clinicId);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch clinic by id: " + clinicId, e);
        }
    }
    @Override
    public Integer addClinic(Clinic clinic) {
        try {
            return clinicDAO.addClinic(clinic);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to add clinic", e);
        }
    }
    @Override
    public void updateClinic(Clinic clinic) {
        try {
            clinicDAO.updateClinic(clinic);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update clinic", e);
        }
    }
    @Override
    public void deleteClinic(int clinicId) {
        try {
            clinicDAO.deleteClinic(clinicId);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete clinic", e);
        }
    }
}